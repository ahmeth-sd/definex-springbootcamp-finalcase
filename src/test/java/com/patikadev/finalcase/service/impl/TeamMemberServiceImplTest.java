package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.TeamMemberNotFoundException;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamMemberServiceImplTest {

    @Mock
    private TeamMemberRepository teamMemberRepository;

    private TeamMember teamMember;

    @InjectMocks
    private TeamMemberServiceImpl teamMemberService;

    @BeforeEach
    void setUp() {
        teamMember = new TeamMember();
        teamMember.setId(1L);
        Users user = new Users();
        user.setId(1L);
        teamMember.setUser(user);
    }

    @Test
    void testGetAllTeamMembers() {
        when(teamMemberRepository.findAll()).thenReturn(List.of(teamMember));

        List<TeamMember> teamMembers = teamMemberService.getAllTeamMembers();

        assertNotNull(teamMembers);
        assertEquals(1, teamMembers.size());
        verify(teamMemberRepository, times(1)).findAll();
    }

    @Test
    void testGetTeamMemberById() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));

        TeamMember foundTeamMember = teamMemberService.getTeamMemberById(1L);

        assertNotNull(foundTeamMember);
        assertEquals(1L, foundTeamMember.getId());
        verify(teamMemberRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTeamMemberByIdNotFound() {
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeamMemberNotFoundException.class, () -> teamMemberService.getTeamMemberById(1L));
        verify(teamMemberRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTeamMember() {
        when(teamMemberRepository.save(teamMember)).thenReturn(teamMember);

        TeamMember createdTeamMember = teamMemberService.createTeamMember(teamMember);

        assertNotNull(createdTeamMember);
        assertEquals(1L, createdTeamMember.getId());
        verify(teamMemberRepository, times(1)).save(teamMember);
    }

    @Test
    void testUpdateTeamMember() {
        TeamMember updatedTeamMember = new TeamMember();
        updatedTeamMember.setUser(new Users());

        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));
        when(teamMemberRepository.save(teamMember)).thenReturn(teamMember);

        TeamMember result = teamMemberService.updateTeamMember(1L, updatedTeamMember);

        assertNotNull(result);
        verify(teamMemberRepository, times(1)).findById(1L);
        verify(teamMemberRepository, times(1)).save(teamMember);
    }

    @Test
    void testDeleteTeamMember() {
        doNothing().when(teamMemberRepository).deleteById(1L);

        teamMemberService.deleteTeamMember(1L);

        verify(teamMemberRepository, times(1)).deleteById(1L);
    }
}