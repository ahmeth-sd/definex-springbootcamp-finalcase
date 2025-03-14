package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.TeamMember;
import java.util.List;

public interface TeamMemberService {
    List<TeamMember> getAllTeamMembers();
    TeamMember getTeamMemberById(Long id);
    TeamMember createTeamMember(TeamMember teamMember);
    TeamMember updateTeamMember(Long id, TeamMember teamMemberDetails);
    void deleteTeamMember(Long id);
}