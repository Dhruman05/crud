package com.example.employee.entity;

import jakarta.persistence.*;

import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer managerId;
    private String managerName;
    private String managerEmail;
    private String managerPhoneNumber;
    private Date createdOn;
}
