package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Comment;
import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.exception.CommentNotFoundException;
import com.patikadev.finalcase.repository.CommentRepository;
import com.patikadev.finalcase.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setId(1L);
        comment.setComment("Test comment");
    }

    @Test
    void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));

        List<Comment> comments = commentService.getAllComments();

        assertNotNull(comments);
        assertEquals(1, comments.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testGetCommentById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.getCommentById(1L);

        assertNotNull(foundComment);
        assertEquals("Test comment", foundComment.getComment());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCommentByIdNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> commentService.getCommentById(1L));
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateComment() {
        Task task = new Task();
        task.setId(1L);
        comment.setTask(task);

        when(commentRepository.save(comment)).thenReturn(comment);

        Comment createdComment = commentService.createComment(comment);

        assertNotNull(createdComment);
        assertEquals("Test comment", createdComment.getComment());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testUpdateComment() {
        Comment updatedComment = new Comment();
        updatedComment.setComment("Updated comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.updateComment(1L, updatedComment);

        assertNotNull(result);
        assertEquals("Updated comment", result.getComment());
        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}