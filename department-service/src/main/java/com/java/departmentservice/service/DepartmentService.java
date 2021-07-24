package com.java.departmentservice.service;

import com.java.departmentservice.entity.Department;
import com.java.departmentservice.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        log.info("Inside saveDepartment method of Department Service ..");
        return departmentRepository.save(department);
    }

    public Department findDepartmentById(Long departmentId){
        log.info("Inside findDepartmentById method of Department Service ..");
        return departmentRepository.findByDepartmentId(departmentId);
    }
}
