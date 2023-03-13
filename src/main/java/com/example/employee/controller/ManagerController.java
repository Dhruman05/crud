package com.example.employee.controller;

import com.example.employee.dto.ManagerEntityDTO;
import com.example.employee.dto.Response;
import com.example.employee.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @PostMapping("/save")
    public Response saveManager(@Valid @RequestBody ManagerEntityDTO managerEntityDTO,Errors error){
        if(error.hasErrors()){
            return new Response("500",error.getAllErrors().get(0).getDefaultMessage(),null);
        }else {
            return managerService.saveManager(managerEntityDTO);
        }
    }
    @GetMapping("/{id}")
    public Response getManagerById(@PathVariable("id") Integer managerId)
    {
        return managerService.getManagerById(managerId);
    }
    @GetMapping("/getAll")
    public Response getAllManager()
    {
        return managerService.getAllManager();
    }
    @PutMapping("/update/{id}")
    public Response updateManager(@Valid @RequestBody ManagerEntityDTO managerEntityDTO,@PathVariable("id")Integer managerId,Errors error)
    {
        if(error.hasErrors()){
            return new Response("500",error.getAllErrors().get(0).getDefaultMessage(),null);
        }else {
            return managerService.updateManager(managerEntityDTO, managerId);
        }
    }
    @DeleteMapping("/delete/{id}")
    public Response deleteManager(@PathVariable("id")Integer managerId)
    {
        return  managerService.deleteManager(managerId);
    }
}
