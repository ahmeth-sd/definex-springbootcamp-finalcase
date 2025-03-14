package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}