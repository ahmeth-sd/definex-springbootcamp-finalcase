package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}