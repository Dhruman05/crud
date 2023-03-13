package com.example.employee.service;

import com.example.employee.dto.EmployeeEntityDTO;
import com.example.employee.dto.Response;


public interface EmployeeService {
    public Response saveEmployee(EmployeeEntityDTO employeeEntityDTO);
    public Response getAllEmployee();
    public Response getEmployeeById(int id);
    public Response updateEmployee(EmployeeEntityDTO employeeEntityDTO, int id);
    public Response deleteEmployee(int id);
}
