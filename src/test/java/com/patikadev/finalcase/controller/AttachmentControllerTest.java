package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Attachment;
import com.patikadev.finalcase.service.AttachmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttachmentControllerTest {

    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private AttachmentController attachmentController;

    private Attachment attachment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attachment = new Attachment();
        attachment.setId(1L); // Set the id field

    }

    @Test
    void testGetAllAttachments() {
        when(attachmentService.getAllAttachments()).thenReturn(List.of(attachment));

        List<Attachment> result = attachmentController.getAllAttachments();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(attachmentService, times(1)).getAllAttachments();
    }

    @Test
    void testGetAttachmentById() {
        when(attachmentService.getAttachmentById(1L)).thenReturn(attachment);

        ResponseEntity<Attachment> result = attachmentController.getAttachmentById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(attachmentService, times(1)).getAttachmentById(1L);
    }

    @Test
    void testCreateAttachment() {
        when(attachmentService.createAttachment(any(Attachment.class))).thenReturn(attachment);

        ResponseEntity<Attachment> result = attachmentController.createAttachment(attachment);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(attachmentService, times(1)).createAttachment(any(Attachment.class));
    }

    @Test
    void testUpdateAttachment() {
        when(attachmentService.updateAttachment(anyLong(), any(Attachment.class))).thenReturn(attachment);

        ResponseEntity<Attachment> result = attachmentController.updateAttachment(1L, attachment);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(attachmentService, times(1)).updateAttachment(anyLong(), any(Attachment.class));
    }

    @Test
    void testDeleteAttachment() {
        doNothing().when(attachmentService).deleteAttachment(1L);

        ResponseEntity<Void> result = attachmentController.deleteAttachment(1L);
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(attachmentService, times(1)).deleteAttachment(1L);
    }
}