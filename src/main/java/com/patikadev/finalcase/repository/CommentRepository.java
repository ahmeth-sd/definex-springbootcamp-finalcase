package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}