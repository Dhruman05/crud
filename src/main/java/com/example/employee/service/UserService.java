package com.example.employee.service;

import com.example.employee.dto.ResetPasswordDTO;
import com.example.employee.dto.Response;
import com.example.employee.dto.UserEntityDTO;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {

    public Response registerUser(UserEntityDTO userEntityDTO) throws MessagingException;
    public Response updateUser(UserEntityDTO userEntityDTO,String userEmail);
    public Response loginUser(UserEntityDTO userEntityDTO);
    public Response verifyUser(String email);
    public Response resetPassword(ResetPasswordDTO resetPasswordDTO);
    public Response forgotPassword(String userEmail) throws MessagingException;
    public Response sendEmail(UserEntityDTO userEntityDTO) throws MessagingException;
}
