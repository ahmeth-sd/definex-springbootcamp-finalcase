package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMember> getTeamMemberById(@PathVariable Long id) {
        TeamMember teamMember = teamMemberService.getTeamMemberById(id);
        return ResponseEntity.ok(teamMember);
    }

    @PostMapping
    public ResponseEntity<TeamMember> createTeamMember(@RequestBody TeamMember teamMember) {
        TeamMember createdTeamMember = teamMemberService.createTeamMember(teamMember);
        return ResponseEntity.ok(createdTeamMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMemberDetails) {
        TeamMember updatedTeamMember = teamMemberService.updateTeamMember(id, teamMemberDetails);
        return ResponseEntity.ok(updatedTeamMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }
}