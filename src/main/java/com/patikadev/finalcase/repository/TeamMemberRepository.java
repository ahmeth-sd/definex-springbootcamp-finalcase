package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Project;
import com.patikadev.finalcase.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByProject(Project project);
    Optional<TeamMember> findByProjectIdAndUserId(Long projectId, Long userId);


}