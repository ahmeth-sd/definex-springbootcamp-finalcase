package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Attachment;
import com.patikadev.finalcase.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Operation(summary = "Get all attachments")
    @GetMapping
    public List<Attachment> getAllAttachments() {
        return attachmentService.getAllAttachments();
    }

    @Operation(summary = "Get an attachment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(attachment);
    }

    @Operation(summary = "Create a new attachment")
    @PostMapping
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        Attachment createdAttachment = attachmentService.createAttachment(attachment);
        return ResponseEntity.ok(createdAttachment);
    }

    @Operation(summary = "Update an existing attachment")
    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id, @RequestBody Attachment attachmentDetails) {
        Attachment updatedAttachment = attachmentService.updateAttachment(id, attachmentDetails);
        return ResponseEntity.ok(updatedAttachment);
    }

    @Operation(summary = "Delete an attachment by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Upload a file")
    @PostMapping("/upload")
    public ResponseEntity<Attachment> uploadFile(@RequestParam("taskId") Long taskId,
                                                 @RequestParam("file") MultipartFile file) {
        Attachment attachment = attachmentService.uploadFile(taskId, file);
        return ResponseEntity.ok(attachment);
    }
}