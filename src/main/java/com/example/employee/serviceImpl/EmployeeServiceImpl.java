package com.example.employee.serviceImpl;

import com.example.employee.dto.EmployeeEntityDTO;
import com.example.employee.dto.Response;
import com.example.employee.entity.EmployeeEntity;
import com.example.employee.exception.EmailAlreadyExistException;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Response saveEmployee(EmployeeEntityDTO employeeEntityDTO) {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            Optional<EmployeeEntity> existingData = employeeRepository.findByNoandEmail(employeeEntityDTO.getEmployeePhoneNumber(), employeeEntityDTO.getEmployeeEmail());
            if (!existingData.isPresent()) {
                BeanUtils.copyProperties(employeeEntityDTO, employeeEntity);
                employeeEntity.setDate(new Date());
                employeeRepository.save(employeeEntity);
                return new Response("200", "data saved successfully", null);
            } else {
                throw new EmailAlreadyExistException("Phone number and email is already exist");
            }
    }

    @Override
    public Response getAllEmployee() {
        List<EmployeeEntity> employeeList = employeeRepository.findAll();
        if (employeeList != null) {
            return new Response("200", "Data fetched successfully", employeeList);
        } else {
            throw new EmployeeNotFoundException("No employee is available having this id");
        }
    }

    @Override
    public Response getEmployeeById(int id) {
        Optional<EmployeeEntity> existingData = employeeRepository.findById(id);
        if (existingData.isPresent()) {
            return new Response("200", "Data fetched successfully", employeeRepository.findById(id).get());
        } else {
            throw new EmployeeNotFoundException("No employee is available having this id");
        }
    }

    @Override
    public Response updateEmployee(EmployeeEntityDTO employeeEntityDTO, int id) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        Optional<EmployeeEntity> existingData = employeeRepository.findById(id);
        if (existingData.isPresent()) {
            employeeEntity = existingData.get();
            Optional<EmployeeEntity> existingEmailAndPhone = employeeRepository.findByNoandEmail(employeeEntity.getEmployeePhoneNumber(), employeeEntity.getEmployeeEmail());
            if (!existingEmailAndPhone.isPresent()) {
                BeanUtils.copyProperties(employeeEntityDTO, employeeEntity);
                employeeRepository.save(employeeEntity);
                return new Response("200", "Data updated successfully", new ArrayList<>());
            } else {
                throw new EmailAlreadyExistException("Phone number and email is already exist");
            }
        } else {
            throw new EmployeeNotFoundException("No employee is available having this id");
        }
    }

    @Override
    public Response deleteEmployee(int id) {
        Optional<EmployeeEntity> existingData = employeeRepository.findById(id);
        if (existingData.isPresent()) {
            employeeRepository.deleteById(id);
            return new Response("200", "Data deleted successfully", new ArrayList<>());
        } else {
            throw new EmployeeNotFoundException("No employee is available having this id");
        }
    }
}
