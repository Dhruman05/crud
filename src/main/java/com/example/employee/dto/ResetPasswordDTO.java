package com.example.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDTO {

    @NotEmpty
    @Email
    private String userEmail;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String newPassword;
}
