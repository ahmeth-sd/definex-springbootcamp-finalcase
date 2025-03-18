package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.exception.ProjectNotFoundException;
import com.patikadev.finalcase.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setId(1L);
        project.setTitle("Sample Project");
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(1L));
    }
}