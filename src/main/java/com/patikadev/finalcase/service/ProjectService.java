package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project createProject(Project project);
    Project updateProject(Long id, Project projectDetails, Users user);
    void deleteProject(Long id, Users user);
    List<TeamMember> getProjectMembers(Long projectId);
}