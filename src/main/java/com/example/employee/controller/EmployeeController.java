package com.example.employee.controller;

import com.example.employee.dto.EmployeeEntityDTO;
import com.example.employee.dto.Response;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/save")
    public Response saveEmployee(@Valid @RequestBody EmployeeEntityDTO employeeEntityDTO, Errors error){
            if(error.hasErrors()){
                return new Response("500",error.getAllErrors().get(0).getDefaultMessage(),null);
            }else {
                return employeeService.saveEmployee(employeeEntityDTO);
            }
        }


    @GetMapping("/getAll")
    public Response getAllEmployee()
    {
        return employeeService.getAllEmployee();
    }
    @GetMapping("/{id}")
    public Response getEmployeeById(@PathVariable("id") Integer employeeId)
    {
            return employeeService.getEmployeeById(employeeId);
    }
    @PutMapping("/update/{id}")
    public Response updateEmployee(@Valid @RequestBody EmployeeEntityDTO employeeEntityDTO,@PathVariable("id") Integer employeeId,Errors error)
    {
        if(error.hasErrors()){
            return new Response("500",error.getAllErrors().get(0).getDefaultMessage(),null);
        }else {
            return employeeService.updateEmployee(employeeEntityDTO, employeeId);
        }
    }
    @DeleteMapping("/delete/{id}")
    public Response deleteEmployeeById(@PathVariable("id") Integer employeeId)
    {
        return  employeeService.deleteEmployee(employeeId);
    }
}
