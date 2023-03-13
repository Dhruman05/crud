package com.example.employee.repository;

import com.example.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Integer> {
    @Query(value = "SELECT * FROM employeetable WHERE employee_phone_number=:employeePhoneNumber OR employee_email=:employeeEmail",nativeQuery = true)
     Optional<EmployeeEntity> findByNoandEmail(String employeePhoneNumber, String employeeEmail);

}
