package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.entity.Users;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department createDepartment(Department department);
    Department updateDepartment(Long id, Department department);
    void deleteDepartment(Long id);
    List<Users> getUsersByDepartment(Long departmentId);  // Updated method signature




}