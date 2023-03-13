package com.example.employee.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;


@Entity
@Table(name="employeetable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employeeId;
    private String employeeName;
    private String employeePhoneNumber;
    private String employeeEmail;
    private String employeeAddress;
    private Integer managerId;
    private Date date;

}
