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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
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
    public void assignUserToDepartmentByEmail(Long departmentId, String email) {
        logger.info("Assigning user with email: {} to department with id: {}", email, departmentId);
        Department department = getDepartmentById(departmentId);
        Users user = userService.getUserByEmail(email);
        user.setDepartment(department);
        userService.updateUser(user.getId(), user);
    }
}