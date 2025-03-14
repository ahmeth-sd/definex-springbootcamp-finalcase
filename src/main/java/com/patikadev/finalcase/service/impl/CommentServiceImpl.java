package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Comment;
import com.patikadev.finalcase.exception.CommentNotFoundException;
import com.patikadev.finalcase.repository.CommentRepository;
import com.patikadev.finalcase.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public List<Comment> getAllComments() {
        logger.info("Fetching all comments");
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id) {
        logger.info("Getting comment by id: {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Comment createComment(Comment comment) {
        logger.info("Creating new comment for task id: {}", comment.getTask().getId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment commentDetails) {
        logger.info("Updating comment with id: {}", id);
        Comment comment = getCommentById(id);
        comment.setComment(commentDetails.getComment());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        logger.info("Deleting comment with id: {}", id);
        commentRepository.deleteById(id);
    }
}