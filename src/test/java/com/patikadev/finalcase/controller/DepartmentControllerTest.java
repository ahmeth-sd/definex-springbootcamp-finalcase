package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department();
        department.setId(1L);
        department.setName("Test Department");
    }

    @Test
    void testGetAllDepartments() {
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));

        List<Department> result = departmentController.getAllDepartments();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void testGetDepartmentById() {
        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        Department result = departmentController.getDepartmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    void testCreateDepartment() {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        Department result = departmentController.createDepartment(department);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }

    @Test
    void testUpdateDepartment() {
        when(departmentService.updateDepartment(anyLong(), any(Department.class))).thenReturn(department);

        Department result = departmentController.updateDepartment(1L, department);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(departmentService, times(1)).updateDepartment(anyLong(), any(Department.class));
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentService).deleteDepartment(1L);

        departmentController.deleteDepartment(1L);
        verify(departmentService, times(1)).deleteDepartment(1L);
    }

}