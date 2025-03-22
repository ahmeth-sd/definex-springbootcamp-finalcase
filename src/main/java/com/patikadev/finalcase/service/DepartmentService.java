package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Department;
import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(UUID id);
    Department createDepartment(Department department);
    Department updateDepartment(UUID id, Department department);
    void deleteDepartment(UUID id);
    void assignUserToDepartmentByEmail(UUID departmentId, String email);

}