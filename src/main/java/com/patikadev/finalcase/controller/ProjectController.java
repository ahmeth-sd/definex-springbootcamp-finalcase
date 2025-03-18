package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

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
        Project updatedProject = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(updatedProject);
    }

    @Operation(summary = "Delete a project by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get members of a project by project ID")
    @GetMapping("/{id}/members")
    public ResponseEntity<List<TeamMember>> getProjectMembers(@PathVariable Long id) {
        List<TeamMember> members = projectService.getProjectMembers(id);
        return ResponseEntity.ok(members);
    }
}