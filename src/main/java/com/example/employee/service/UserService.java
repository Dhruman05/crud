package com.example.employee.service;

import com.example.employee.dto.Response;
import com.example.employee.dto.UserEntityDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {

    public Response registerUser(UserEntityDTO userEntityDTO);
    public Response updateUser(UserEntityDTO userEntityDTO,String userEmail);
    public Response loginUser(UserEntityDTO userEntityDTO);
    public Response verifyUser(String email,String password);
}
