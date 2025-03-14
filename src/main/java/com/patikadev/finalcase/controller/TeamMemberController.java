package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @Operation(summary = "Get all team members")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the team members"),
            @ApiResponse(responseCode = "404", description = "Team members not found")
    })
    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @Operation(summary = "Get a team member by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the team member"),
            @ApiResponse(responseCode = "404", description = "Team member not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamMember> getTeamMemberById(@PathVariable Long id) {
        TeamMember teamMember = teamMemberService.getTeamMemberById(id);
        return ResponseEntity.ok(teamMember);
    }

    @Operation(summary = "Create a new team member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<TeamMember> createTeamMember(@RequestBody TeamMember teamMember) {
        TeamMember createdTeamMember = teamMemberService.createTeamMember(teamMember);
        return ResponseEntity.ok(createdTeamMember);
    }

    @Operation(summary = "Update an existing team member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMemberDetails) {
        TeamMember updatedTeamMember = teamMemberService.updateTeamMember(id, teamMemberDetails);
        return ResponseEntity.ok(updatedTeamMember);
    }

    @Operation(summary = "Delete a team member by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Team member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Team member not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }
}