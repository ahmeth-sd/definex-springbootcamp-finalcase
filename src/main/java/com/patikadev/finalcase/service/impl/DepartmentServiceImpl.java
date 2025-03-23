package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.DepartmentNotFoundException;
import com.patikadev.finalcase.repository.DepartmentRepository;
import com.patikadev.finalcase.service.DepartmentService;
import com.patikadev.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    @Transactional
    public List<Department> getAllDepartments() {
        logger.info("Fetching all departments");
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        logger.info("Getting department by id: {}", id);
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    @Override
    public Department createDepartment(Department department) {
        logger.info("Creating new department with name: {}", department.getName());
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        logger.info("Updating department with id: {}", id);
        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        logger.info("Deleting department with id: {}", id);
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Users> getUsersByDepartment(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        return department.map(Department::getUsers).orElse(null);
    }



}