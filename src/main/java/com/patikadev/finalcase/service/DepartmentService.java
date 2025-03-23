package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Department;
import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department createDepartment(Department department);
    Department updateDepartment(Long id, Department department);
    void deleteDepartment(Long id);
    void assignUserToDepartmentByEmail(Long departmentId, String email);

}