package com.example.employee.serviceImpl;

import com.example.employee.dto.Response;
import com.example.employee.dto.UserEntityDTO;
import com.example.employee.entity.UserEntity;
import com.example.employee.exception.EmailAlreadyExistException;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.repository.UserRepository;
import com.example.employee.service.UserService;
import com.example.employee.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Response registerUser(UserEntityDTO userEntityDTO) {
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> existingData = userRepository.findByEmail(userEntityDTO.getUserEmail());
        if (!existingData.isPresent()) {
            userEntity.setUserPassword(passwordEncoder.encode(userEntityDTO.getUserPassword()));
            userEntity.setUserEmail(userEntityDTO.getUserEmail());
            userEntity.setUserRole(userEntityDTO.getUserRole());
            userEntity.setIs_verified(false);
            userRepository.save(userEntity);
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

        Optional<UserEntity> existingData = userRepository.findByEmailAndPassword(userEntityDTO.getUserEmail(), userEntityDTO.getUserPassword());
        if (!existingData.isPresent()) {
            return new Response("400", "Credentials are wrong", null);
        }
        String token = jwtUtil.generateJwt(userEntityDTO);
        return new Response("200", "user logged in and token generated", token);
    }

    @Override
    public Response verifyUser(String userEmail, String userPassword) {
        Optional<UserEntity> existingData = userRepository.findByEmail(userEmail);
        if (existingData.isPresent()) {
            UserEntity user = existingData.get();
            if (user.getUserPassword().equalsIgnoreCase(userPassword)) {
                user.setIs_verified(true);
                userRepository.save(user);
            }
            return new Response("200", "User verified successfully", null);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public Response resetPassword(String userEmail, String oldPassword, String newPassword) {
        Optional<UserEntity> existingData = userRepository.findByEmail(userEmail);
        if (existingData.isPresent()) {
            UserEntity user = existingData.get();
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatches = bcrypt.matches(oldPassword, user.getUserPassword());
            if (isPasswordMatches) {
                user.setUserPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return new Response("200", "password reset successfully", null);
            } else {
                return new Response("400", "old password is not matching", null);
            }
        } else {
            return new Response("404", "user not found", null);
        }
    }


}