package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment commentDetails);
    void deleteComment(Long id);
}