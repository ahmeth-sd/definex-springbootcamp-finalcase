package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Comment;
import com.patikadev.finalcase.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comment = new Comment();
        comment.setId(1L);
    }

        @Test
        void testGetAllComments() {
            when(commentService.getAllComments()).thenReturn(List.of(comment));

            List<Comment> result = commentController.getAllComments();
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(commentService, times(1)).getAllComments();
        }

        @Test
        void testGetCommentById() {
            when(commentService.getCommentById(1L)).thenReturn(comment);

            ResponseEntity<Comment> result = commentController.getCommentById(1L);
            assertNotNull(result);
            assertEquals(1L, result.getBody().getId());
            verify(commentService, times(1)).getCommentById(1L);
        }

        @Test
        void testCreateComment() {
            when(commentService.createComment(any(Comment.class))).thenReturn(comment);

            ResponseEntity<Comment> result = commentController.createComment(comment);
            assertNotNull(result);
            assertEquals(1L, result.getBody().getId());
            verify(commentService, times(1)).createComment(any(Comment.class));
        }

        @Test
        void testUpdateComment() {
            when(commentService.updateComment(anyLong(), any(Comment.class))).thenReturn(comment);

            ResponseEntity<Comment> result = commentController.updateComment(1L, comment);
            assertNotNull(result);
            assertEquals(1L, result.getBody().getId());
            verify(commentService, times(1)).updateComment(anyLong(), any(Comment.class));
        }

        @Test
        void testDeleteComment() {
            doNothing().when(commentService).deleteComment(1L);

            ResponseEntity<Void> result = commentController.deleteComment(1L);
            assertNotNull(result);
            assertEquals(204, result.getStatusCodeValue());
            verify(commentService, times(1)).deleteComment(1L);
        }
    }