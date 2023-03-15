package com.example.employee.repository;

import com.example.employee.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query(value = "SELECT * FROM user_entity WHERE user_email = :userEmail", nativeQuery = true)
    Optional<UserEntity> findByEmail(String userEmail);

    @Query(value = "SELECT * FROM user_entity WHERE user_email = :userEmail", nativeQuery = true)
    UserEntity findByOnlyEmail(String userEmail);

    @Query(value = "SELECT * FROM user_entity WHERE user_email=:userEmail AND user_password=:userPassword", nativeQuery = true)
    UserEntity findByEmailAndPassword(String userEmail, String userPassword);

}
