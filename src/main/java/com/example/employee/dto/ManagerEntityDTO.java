package com.example.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerEntityDTO {
    @NotEmpty(message = "Manager name should not be empty")
    @Size(min = 3,message = "Manager name should be more than 2 letters")
    private String managerName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String managerEmail;

    @NotEmpty(message = "phone number should not be empty")
    @Size(max = 10,min = 10,message = "maximum 10 digits")
    private String managerPhoneNumber;

}
