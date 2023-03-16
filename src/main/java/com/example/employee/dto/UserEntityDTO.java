package com.example.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntityDTO {
    @NotEmpty
    @Email
    private String userEmail;
    @NotEmpty
    private String userPassword;

    private String userRole;
    private Boolean is_verified;

}
