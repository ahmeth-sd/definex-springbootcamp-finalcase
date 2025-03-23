package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Attachment;
import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.exception.AttachmentNotFoundException;
import com.patikadev.finalcase.exception.TaskNotFoundException;
import com.patikadev.finalcase.repository.AttachmentRepository;
import com.patikadev.finalcase.repository.TaskRepository;
import com.patikadev.finalcase.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    private static final String UPLOAD_DIR = "uploads/";


    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Override
    public List<Attachment> getAllAttachments() {
        logger.info("Fetching all attachments");
        return attachmentRepository.findAll();
    }

    @Override
    public Attachment getAttachmentById(Long id) {
        logger.info("Getting attachment by id: {}", id);
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new AttachmentNotFoundException(id));
    }

    @Override
    public Attachment createAttachment(Attachment attachment) {
        logger.info("Creating new attachment for task id: {}", attachment.getTask().getId());
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment updateAttachment(Long id, Attachment attachmentDetails) {
        logger.info("Updating attachment with id: {}", id);
        Attachment attachment = getAttachmentById(id);
        attachment.setFilePath(attachmentDetails.getFilePath());
        return attachmentRepository.save(attachment);
    }

    @Override
    public void deleteAttachment(Long id) {
        logger.info("Deleting attachment with id: {}", id);
        attachmentRepository.deleteById(id);
    }

    @Override
    public Attachment uploadFile(Long taskId, MultipartFile file) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setFilePath(filePath.toString());
        return attachmentRepository.save(attachment);
    }
}