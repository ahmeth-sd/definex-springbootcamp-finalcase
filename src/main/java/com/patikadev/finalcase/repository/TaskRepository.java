package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}