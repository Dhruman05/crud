package com.example.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class EmployeeEntityDTO {
    @NotEmpty(message = "employee name should not be empty")
    @Size(min = 3,message = "Name should be more than 2 letters")
    private String employeeName;

    @NotEmpty(message = "phone number should not be empty")
    @Size(min = 10,max = 10,message = "Phone number should not be more than 10 digits")
    private String employeePhoneNumber;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String employeeEmail;

    @Size(min = 10,message = "Address should be more than 10 letters")
    private String employeeAddress;

    private Integer managerId;

}
