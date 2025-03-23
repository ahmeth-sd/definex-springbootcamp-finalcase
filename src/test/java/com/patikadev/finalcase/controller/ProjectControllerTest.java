package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.service.DepartmentService;
import com.patikadev.finalcase.service.ProjectService;
import com.patikadev.finalcase.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private Users user;
    private Department department;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        project = new Project();
        project.setId(1L);
        project.setTitle("Test Project");

        user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");

        department = new Department();
        department.setId(1L);
        department.setName("Test Department");
    }

    @Test
    void testGetAllProjects() {
        when(projectService.getAllProjects()).thenReturn(List.of(project));

        List<Project> result = projectController.getAllProjects();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testGetProjectById() {
        when(projectService.getProjectById(1L)).thenReturn(project);

        ResponseEntity<Project> result = projectController.getProjectById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void testCreateProject() {
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(projectService.createProject(any(Project.class))).thenReturn(project);

        ResponseEntity<Project> result = projectController.createProject(1L, project);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(departmentService, times(1)).getDepartmentById(1L);
        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    void testUpdateProject() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(projectService.updateProject(anyLong(), any(Project.class), any(Users.class))).thenReturn(project);

        ResponseEntity<Project> result = projectController.updateProject(1L, project);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(projectService, times(1)).updateProject(anyLong(), any(Project.class), any(Users.class));
    }

    @Test
    void testDeleteProject() {
        // Create a spy of the ProjectController
        ProjectController spyController = spy(projectController);

        // Mock the getCurrentUser method to return the expected user
        doReturn(user).when(spyController).getCurrentUser();

        // Mock the project retrieval and deletion
        project.setDeleted(false);
        when(projectService.getProjectById(1L)).thenReturn(project);
        doAnswer(invocation -> {
            Long projectId = invocation.getArgument(0);
            Users user = invocation.getArgument(1);
            Project proj = projectService.getProjectById(projectId);
            proj.setDeleted(true);
            return null;
        }).when(projectService).deleteProject(1L, user);

        ResponseEntity<Void> result = spyController.deleteProject(1L);
        assertNotNull(result);
        assertEquals(204, result.getStatusCode().value());
        assertTrue(project.isDeleted());
        verify(projectService, times(1)).deleteProject(1L, user);
    }
}