package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.ProjectNotFoundException;
import com.patikadev.finalcase.exception.UnauthorizedException;
import com.patikadev.finalcase.repository.ProjectRepository;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import com.patikadev.finalcase.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        logger.info("Getting project by id: {}", id);
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public Project createProject(Project project) {
        logger.info("Creating new project with title: {}", project.getTitle());
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails, Users user) {
        logger.info("Updating project with id: {}", id);
        Project project = getProjectById(id);
        validateUserPermissions(user, project);
        project.setTitle(projectDetails.getTitle());
        project.setDescription(projectDetails.getDescription());
        project.setStatus(projectDetails.getStatus());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id, Users user) {
        logger.info("Deleting project with id: {}", id);
        Project project = getProjectById(id);
        validateUserPermissions(user, project);
        project.setDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public List<TeamMember> getProjectMembers(Long projectId) {
        logger.info("Fetching members for project id: {}", projectId);
        Project project = getProjectById(projectId);
        return teamMemberRepository.findByProject(project);
    }

    private void validateUserPermissions(Users user, Project project) {
        TeamMember teamMember = teamMemberRepository.findByProjectIdAndUserId(project.getId(), user.getId())
                .orElseThrow(() -> new UnauthorizedException("User is not a member of the project."));

        if (teamMember.getRole() != TeamMember.Role.PROJECT_MANAGER) {
            throw new UnauthorizedException("Only Project Manager can perform this action.");
        }
    }
}