package com.example.employee.serviceImpl;

import com.example.employee.dto.ResetPasswordDTO;
import com.example.employee.dto.Response;
import com.example.employee.dto.UserEntityDTO;
import com.example.employee.entity.UserEntity;
import com.example.employee.exception.EmailAlreadyExistException;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.repository.UserRepository;
import com.example.employee.service.EmailService;
import com.example.employee.service.UserService;
import com.example.employee.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;


    @Override
    public Response registerUser(UserEntityDTO userEntityDTO) throws MessagingException {
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> existingData = userRepository.findByEmail(userEntityDTO.getUserEmail());
        if (!existingData.isPresent()) {
            userEntity.setUserPassword(passwordEncoder.encode(userEntityDTO.getUserPassword()));
            userEntity.setUserEmail(userEntityDTO.getUserEmail());
            userEntity.setUserRole(userEntityDTO.getUserRole());
            userEntity.setIs_verified(false);
            userRepository.save(userEntity);
            emailService.sendVerifyEmail(userEntityDTO);
            return new Response("200", "User registered successfully", null);
        } else {
            throw new EmailAlreadyExistException("Email is already registered");
        }
    }

    @Override
    public Response updateUser(UserEntityDTO userEntityDTO, String userEmail) {
        UserEntity user = new UserEntity();
        Optional<UserEntity> existingData = userRepository.findByEmail(userEntityDTO.getUserEmail());
        if (existingData.isPresent()) {
            user = existingData.get();
            BeanUtils.copyProperties(userEntityDTO, user);
            userRepository.save(user);
            return new Response("200", "Data updated successfully", null);
        } else {
            throw new UserNotFoundException("No user available having this email");
        }
    }

    @Override
    public Response loginUser(UserEntityDTO userEntityDTO) {

        UserEntity existingData = userRepository.findByEmailAndPassword(userEntityDTO.getUserEmail(), userEntityDTO.getUserPassword());
        if (existingData != null) {
            return new Response("400", "Credentials are wrong", null);
        }
        String token = jwtUtil.generateJwt(userEntityDTO);
        return new Response("200", "user logged in and token generated", token);
    }

    @Override
    public Response verifyUser(String email) {
        Optional<UserEntity> existingData = userRepository.findByEmail(email);
        if (existingData.isPresent()) {
            existingData.get().setIs_verified(true);
            userRepository.save(existingData.get());
            return new Response("200", "User verified successfully", null);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public Response resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<UserEntity> existingData = userRepository.findByEmail(resetPasswordDTO.getUserEmail());
        if (existingData.isPresent()) {
            UserEntity user = existingData.get();
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatches = bcrypt.matches(resetPasswordDTO.getUserPassword(), user.getUserPassword());
            if (isPasswordMatches) {
                user.setUserPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
                userRepository.save(user);
                return new Response("200", "password reset successfully", null);
            } else {
                return new Response("400", "old password is not matching", null);
            }
        } else {
            return new Response("404", "user not found", null);
        }
    }

    @Override
    public Response forgotPassword(String email) throws MessagingException {
        Optional<UserEntity> existingData = userRepository.findByEmail(email);
        UserEntity user = existingData.get();
        if (existingData.isPresent()) {
            emailService.sendResetPassword(user);
            return new Response("200", "Email sent successfully", null);
        } else {
            return new Response("404", "User does not exist having this email id", user.getUserEmail());
        }

    }

    @Override
    public Response sendEmail(UserEntityDTO userEntityDTO) throws MessagingException {
        Optional<UserEntity> existingData = userRepository.findByEmail(userEntityDTO.getUserEmail());
        if (existingData.isPresent()) {
            emailService.sendEmail(userEntityDTO);
            return new Response("200", "email sent successfully", null);
        } else {
            return new Response("404", "email not found", null);
        }
    }

}