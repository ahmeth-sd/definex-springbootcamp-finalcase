package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Attachment;
import com.patikadev.finalcase.exception.AttachmentNotFoundException;
import com.patikadev.finalcase.repository.AttachmentRepository;
import com.patikadev.finalcase.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

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
}