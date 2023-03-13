package com.example.employee.controller;

import com.example.employee.dto.Response;
import com.example.employee.dto.UserEntityDTO;
import com.example.employee.service.UserService;
import com.example.employee.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/register")
    public Response registerUser(@Valid @RequestBody UserEntityDTO userEntityDTO, Errors error) {
        if (error.hasErrors()) {
            return new Response("500", error.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return userService.registerUser(userEntityDTO);
        }
    }

    @PutMapping("/update/{email}")
    public Response updateUser(@Valid @RequestBody UserEntityDTO userEntityDTO, @PathVariable("email") String userEmail) throws Exception {
        return userService.updateUser(userEntityDTO, userEmail);
    }

    @PostMapping("/login")
    public Response loginUser(@Valid @RequestBody UserEntityDTO userEntityDTO, Errors errors) {

        if (errors.hasErrors()) {
            return new Response("500", errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return userService.loginUser(userEntityDTO);
        }
    }

    @PostMapping("/forgot")
    public Response forgotPassword(@RequestBody UserEntityDTO userEntityDTO){
        return null;
    }
}
