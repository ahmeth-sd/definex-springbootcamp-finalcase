package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}