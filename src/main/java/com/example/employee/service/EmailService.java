package com.example.employee.service;

import com.example.employee.dto.UserEntityDTO;
import com.example.employee.entity.UserEntity;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendEmail(UserEntityDTO userEntityDTO) throws MessagingException;

    public void sendResetPassword(UserEntity userEntity) throws MessagingException;

    public void sendVerifyEmail(UserEntityDTO userEntityDTO) throws MessagingException;
}
