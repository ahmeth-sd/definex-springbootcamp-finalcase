package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "Get all departments")
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @Operation(summary = "Get a department by ID")
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable UUID id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(summary = "Create a new department")
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @Operation(summary = "Update an existing department")
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable UUID id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @Operation(summary = "Delete a department by ID")
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
    }

    @Operation(summary = "Assign a user to a department by email")
    @PostMapping("/{id}/assign-user")
    public void assignUserToDepartmentByEmail(@PathVariable UUID id, @RequestParam String email) {
        departmentService.assignUserToDepartmentByEmail(id, email);
    }
}