package com.example.employee.service;

import com.example.employee.dto.ManagerEntityDTO;
import com.example.employee.dto.Response;
import com.example.employee.entity.ManagerEntity;

public interface ManagerService {
        public Response saveManager(ManagerEntityDTO managerEntityDTO);
        public Response getManagerById(int id);
        public Response getAllManager();
        public Response updateManager(ManagerEntityDTO managerEntityDTO,int id);
        public Response deleteManager(int id);
}
