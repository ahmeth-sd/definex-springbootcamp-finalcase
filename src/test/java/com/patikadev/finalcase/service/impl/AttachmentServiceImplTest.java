package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Attachment;
import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.exception.AttachmentNotFoundException;
import com.patikadev.finalcase.repository.AttachmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AttachmentServiceImplTest {

    @Mock
    private AttachmentRepository attachmentRepository;

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    private Attachment attachment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attachment = new Attachment();
        attachment.setId(1L);
        attachment.setFilePath("path/to/file");
    }

    @Test
    void testGetAllAttachments() {
        when(attachmentRepository.findAll()).thenReturn(List.of(new Attachment(), new Attachment()));

        var result = attachmentService.getAllAttachments();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(attachmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAttachmentById_NotFound() {
        when(attachmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AttachmentNotFoundException.class, () -> attachmentService.getAttachmentById(1L));
    }

    @Test
    void testGetAttachmentById_Found() {
        when(attachmentRepository.findById(anyLong())).thenReturn(Optional.of(attachment));

        var result = attachmentService.getAttachmentById(1L);
        assertNotNull(result);
        assertEquals("path/to/file", result.getFilePath());
        verify(attachmentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateAttachment() {
        Task task = new Task();
        task.setId(1L);
        attachment.setTask(task);

        when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);

        var result = attachmentService.createAttachment(attachment);
        assertNotNull(result);
        assertEquals("path/to/file", result.getFilePath());
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    void testUpdateAttachment_NotFound() {
        when(attachmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Attachment updatedAttachment = new Attachment();
        updatedAttachment.setFilePath("new/path/to/file");

        assertThrows(AttachmentNotFoundException.class, () -> attachmentService.updateAttachment(1L, updatedAttachment));
    }

    @Test
    void testUpdateAttachment_Found() {
        when(attachmentRepository.findById(anyLong())).thenReturn(Optional.of(attachment));
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);

        Attachment updatedAttachment = new Attachment();
        updatedAttachment.setFilePath("new/path/to/file");

        var result = attachmentService.updateAttachment(1L, updatedAttachment);
        assertNotNull(result);
        assertEquals("new/path/to/file", result.getFilePath());
        verify(attachmentRepository, times(1)).save(attachment);
    }

    @Test
    void testDeleteAttachment() {
        doNothing().when(attachmentRepository).deleteById(anyLong());

        attachmentService.deleteAttachment(1L);
        verify(attachmentRepository, times(1)).deleteById(1L);
    }
}