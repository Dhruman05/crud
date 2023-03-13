package com.example.employee.serviceImpl;

import com.example.employee.dto.ManagerEntityDTO;
import com.example.employee.dto.Response;
import com.example.employee.entity.ManagerEntity;
import com.example.employee.exception.EmailAlreadyExistException;
import com.example.employee.exception.ManagerNotFoundException;
import com.example.employee.exception.UserNotFoundException;
import com.example.employee.repository.ManagerRepository;
import com.example.employee.service.ManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    @Override
    public Response saveManager(ManagerEntityDTO managerEntityDTO) {
        ManagerEntity managerEntity = new ManagerEntity();
        Optional<ManagerEntity> existingData =
                managerRepository.findByNoAndEmail(managerEntityDTO.getManagerPhoneNumber(), managerEntityDTO.getManagerEmail());
        if (!existingData.isPresent()) {
            BeanUtils.copyProperties(managerEntityDTO, managerEntity);
            managerEntity.setCreatedOn(new Date());
            managerRepository.save(managerEntity);
            return new Response("200", "Data saved successfully", new ArrayList<>());
        } else {
            throw new ManagerNotFoundException("Phone number and email is already exist");
        }
    }

    @Override
    public Response getManagerById(int id) {
        Optional<ManagerEntity> existingData = managerRepository.findById(id);
        if (existingData.isPresent()) {
            return new Response("200", "Data fetched successfully", managerRepository.findById(id).get());
        } else {
            throw new ManagerNotFoundException("No manager is available having this id");
        }
    }

    @Override
    public Response getAllManager() {
        List<ManagerEntity> managerEntityList = managerRepository.findAll();
        if (managerEntityList != null) {
            return new Response("200", "Data fetched successfully", managerEntityList);
        } else {
            throw new ManagerNotFoundException("No manager is available having this id");
        }
    }

    @Override
    public Response updateManager(ManagerEntityDTO managerEntityDTO, int id) {
        ManagerEntity managerEntity = new ManagerEntity();
        Optional<ManagerEntity> existingData = managerRepository.findById(id);
        if (existingData.isPresent()) {
            managerEntity = existingData.get();
            Optional<ManagerEntity> existingPhoneAndEmail = managerRepository.findByNoAndEmail(managerEntity.getManagerPhoneNumber(), managerEntity.getManagerEmail());
            if (!existingPhoneAndEmail.isPresent()) {
                BeanUtils.copyProperties(managerEntityDTO, managerEntity);
                return new Response("200", "Data updated successfully", managerRepository.save(managerEntity));
            } else {
                throw new EmailAlreadyExistException("Phone number and email is already exist");
            }
        } else {
            throw new ManagerNotFoundException("No manager is available having this id");
        }
    }

    @Override
    public Response deleteManager(int id) {
        Optional<ManagerEntity> existingData = managerRepository.findById(id);
        if (existingData.isPresent()) {
            managerRepository.deleteById(id);
            return new Response("200", "Manager deleted successfully", new ArrayList<>());
        } else {
            throw new ManagerNotFoundException("No manager is available having this id");
        }
    }
}
