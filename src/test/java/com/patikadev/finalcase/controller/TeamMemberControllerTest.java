package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.service.TeamMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TeamMemberControllerTest {
    
    @Mock
    private TeamMemberService teamMemberService;

    @InjectMocks
    private TeamMemberController teamMemberController;

    private TeamMember teamMember;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamMember = new TeamMember();
        teamMember.setId(1L);
    }

    @Test
    void testGetTeamMemberById() {
        when(teamMemberService.getTeamMemberById(1L)).thenReturn(teamMember);

        ResponseEntity<TeamMember> result = teamMemberController.getTeamMemberById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(teamMemberService, times(1)).getTeamMemberById(1L);
    }

    @Test
    void testCreateTeamMember() {
        when(teamMemberService.createTeamMember(any(TeamMember.class))).thenReturn(teamMember);

        ResponseEntity<TeamMember> result = teamMemberController.createTeamMember(teamMember);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(teamMemberService, times(1)).createTeamMember(any(TeamMember.class));
    }

    @Test
    void testUpdateTeamMember() {
        when(teamMemberService.updateTeamMember(anyLong(), any(TeamMember.class))).thenReturn(teamMember);

        ResponseEntity<TeamMember> result = teamMemberController.updateTeamMember(1L, teamMember);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(teamMemberService, times(1)).updateTeamMember(anyLong(), any(TeamMember.class));
    }

    @Test
    void testDeleteTeamMember() {
        doNothing().when(teamMemberService).deleteTeamMember(1L);

        ResponseEntity<Void> result = teamMemberController.deleteTeamMember(1L);
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(teamMemberService, times(1)).deleteTeamMember(1L);
    }

    @Test
    void testGetAllTeamMembers() {
        when(teamMemberService.getAllTeamMembers()).thenReturn(List.of(teamMember));

        List<TeamMember> result = teamMemberController.getAllTeamMembers();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(teamMemberService, times(1)).getAllTeamMembers();
    }

}
