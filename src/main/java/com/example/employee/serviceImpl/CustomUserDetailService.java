package com.example.employee.serviceImpl;

import com.example.employee.entity.UserEntity;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UserNotFoundException{
        UserEntity userEntity = null;
        UserEntity existingData = userRepository.findByOnlyEmail(userEmail);
        if (existingData!=null) {
            userEntity = existingData;
        }
        if(userEntity == null){
            System.out.println("User not found");
        }

        UserDetails user = User.withUsername(userEntity.getUserEmail()).password(userEntity.getUserPassword()).authorities("EMPLOYEE").build();
        return user;
    }
}
