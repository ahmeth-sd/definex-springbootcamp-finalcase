package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.DepartmentNotFoundException;
import com.patikadev.finalcase.repository.DepartmentRepository;
import com.patikadev.finalcase.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;
    private Long departmentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentId = 1L;
        department = new Department();
        department.setId(departmentId);
    }

    @Test
    void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(new Department(), new Department()));

        var result = departmentService.getAllDepartments();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(departmentId));
    }

    @Test
    void testGetDepartmentById_Found() {
        department.setName("Engineering");
        when(departmentRepository.findById(any(Long.class))).thenReturn(Optional.of(department));

        var result = departmentService.getDepartmentById(departmentId);
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void testCreateDepartment() {
        department.setName("Engineering");
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        var result = departmentService.createDepartment(department);
        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testUpdateDepartment_NotFound() {
        when(departmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Department updatedDepartment = new Department();
        updatedDepartment.setName("Marketing");

        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(departmentId, updatedDepartment));
    }

    @Test
    void testUpdateDepartment_Found() {
        when(departmentRepository.findById(any(Long.class))).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department updatedDepartment = new Department();
        updatedDepartment.setName("Marketing");

        var result = departmentService.updateDepartment(departmentId, updatedDepartment);
        assertNotNull(result);
        assertEquals("Marketing", result.getName());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentRepository).deleteById(any(Long.class));

        departmentService.deleteDepartment(departmentId);
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

}