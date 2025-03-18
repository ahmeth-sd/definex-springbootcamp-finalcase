package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.service.ProjectService;
import com.patikadev.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all projects")
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Operation(summary = "Get a project by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Create a new project")
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }

    @Operation(summary = "Update an existing project")
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Users currentUser = getCurrentUser();
        Project updatedProject = projectService.updateProject(id, projectDetails, currentUser);
        return ResponseEntity.ok(updatedProject);
    }

    @Operation(summary = "Delete a project by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Users currentUser = getCurrentUser();
        projectService.deleteProject(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    private Users getCurrentUser() {
        // Implement this method to retrieve the current user from the security context or session
        return userService.getUserByEmail("current_user_email@example.com"); // Replace with actual user retrieval logic
    }
}