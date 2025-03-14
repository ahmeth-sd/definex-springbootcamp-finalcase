package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachments, Long> {
}