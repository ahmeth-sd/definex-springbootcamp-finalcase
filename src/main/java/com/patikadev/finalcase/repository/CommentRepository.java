package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}