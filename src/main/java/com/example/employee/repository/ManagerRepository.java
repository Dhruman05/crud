package com.example.employee.repository;

import com.example.employee.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<ManagerEntity,Integer> {
    @Query(value = "SELECT * FROM manager WHERE manager_phone_number=:managerPhoneNumber OR manager_email=:managerEmail",nativeQuery = true)
    Optional<ManagerEntity> findByNoAndEmail(String managerPhoneNumber, String managerEmail);
}
