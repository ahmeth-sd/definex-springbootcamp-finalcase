package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}