package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.exception.TeamMemberNotFoundException;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import com.patikadev.finalcase.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    @Override
    public List<TeamMember> getAllTeamMembers() {
        logger.info("Fetching all team members");
        return teamMemberRepository.findAll();
    }

    @Override
    public TeamMember getTeamMemberById(Long id) {
        logger.info("Getting team member by id: {}", id);
        return teamMemberRepository.findById(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));
    }

    @Override
    @Transactional
    public TeamMember createTeamMember(TeamMember teamMember) {
        logger.info("Creating new team member with user id: {}", teamMember.getUser().getId());
        return teamMemberRepository.save(teamMember);
    }

    @Override
    @Transactional
    public TeamMember updateTeamMember(Long id, TeamMember teamMemberDetails) {
        logger.info("Updating team member with id: {}", id);
        TeamMember teamMember = getTeamMemberById(id);
        teamMember.setProject(teamMemberDetails.getProject());
        teamMember.setUser(teamMemberDetails.getUser());
        teamMember.setRole(teamMemberDetails.getRole());
        return teamMemberRepository.save(teamMember);
    }

    @Override
    @Transactional
    public void deleteTeamMember(Long id) {
        logger.info("Deleting team member with id: {}", id);
        teamMemberRepository.deleteById(id);
    }
}