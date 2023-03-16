package com.example.employee.serviceImpl;

import com.example.employee.dto.UserEntityDTO;
import com.example.employee.entity.UserEntity;
import com.example.employee.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmail(UserEntityDTO userEntityDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(email);
        helper.setTo(userEntityDTO.getUserEmail());
        helper.setSubject("Test");
        helper.setText("Test email");
        javaMailSender.send(message);
    }

    @Override
    public void sendResetPassword(UserEntity userEntity) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(email);
        helper.setTo(userEntity.getUserEmail());
        helper.setSubject("forgot password");
        helper.setText("Click here to create new password " + "http://localhost:8888/user/reset?oldpassword=" + userEntity.getUserPassword() + "&email=" + userEntity.getUserEmail());
        javaMailSender.send(message);
    }

    @Override
    public void sendVerifyEmail(UserEntityDTO userEntityDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(email);
        helper.setFrom(email);
        helper.setTo(userEntityDTO.getUserEmail());
        helper.setSubject("verify");
        helper.setText("Click here to verify yourself " + "http://localhost:8888/user/verify?email=" + userEntityDTO.getUserEmail());
        javaMailSender.send(message);
    }
}
