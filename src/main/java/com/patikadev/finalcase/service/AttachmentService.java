package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    List<Attachment> getAllAttachments();
    Attachment getAttachmentById(Long id);
    Attachment createAttachment(Attachment attachment);
    Attachment updateAttachment(Long id, Attachment attachmentDetails);
    void deleteAttachment(Long id);
    Attachment uploadFile(Long taskId, MultipartFile file);

}