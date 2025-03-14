package com.patikadev.finalcase.entity;

import com.patikadev.finalcase.entity.Task;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(length = 255, nullable = false)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onUpload() {
        uploadedAt = LocalDateTime.now();
    }
}