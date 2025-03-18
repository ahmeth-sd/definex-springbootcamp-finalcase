package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.ProjectNotFoundException;
import com.patikadev.finalcase.exception.UnauthorizedException;
import com.patikadev.finalcase.repository.ProjectRepository;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private Users projectManager;
    private Users teamLeader;
    private TeamMember projectManagerEntity;
    private TeamMember teamLeaderEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setId(1L);
        project.setTitle("Sample Project");

        projectManager = new Users();
        projectManager.setId(1L);

        teamLeader = new Users();
        teamLeader.setId(2L);

        projectManagerEntity = new TeamMember();
        projectManagerEntity.setUser(projectManager);
        projectManagerEntity.setRole(TeamMember.Role.PROJECT_MANAGER);

        teamLeaderEntity = new TeamMember();
        teamLeaderEntity.setUser(teamLeader);
        teamLeaderEntity.setRole(TeamMember.Role.TEAM_LEADER);
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(new Project(), new Project()));

        var result = projectService.getAllProjects();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById_NotFound() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(1L));
    }

    @Test
    void testGetProjectById_Found() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));

        var result = projectService.getProjectById(1L);
        assertNotNull(result);
        assertEquals("Sample Project", result.getTitle());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        var result = projectService.createProject(project);
        assertNotNull(result);
        assertEquals("Sample Project", result.getTitle());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject_Unauthorized() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Project updatedProject = new Project();
        updatedProject.setTitle("Updated Project");

        assertThrows(UnauthorizedException.class, () -> projectService.updateProject(1L, updatedProject, teamLeader));
    }

    @Test
    void testUpdateProject_Authorized() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(projectManagerEntity));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project updatedProject = new Project();
        updatedProject.setTitle("Updated Project");

        var result = projectService.updateProject(1L, updatedProject, projectManager);
        assertNotNull(result);
        assertEquals("Updated Project", result.getTitle());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testDeleteProject_Unauthorized() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        assertThrows(UnauthorizedException.class, () -> projectService.deleteProject(1L, teamLeader));
    }

    @Test
    void testDeleteProject_Authorized() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(projectManagerEntity));

        projectService.deleteProject(1L, projectManager);
        assertTrue(project.isDeleted());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testGetProjectMembers() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(teamMemberRepository.findByProject(any(Project.class))).thenReturn(List.of(new TeamMember(), new TeamMember()));

        var result = projectService.getProjectMembers(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamMemberRepository, times(1)).findByProject(project);
    }
}