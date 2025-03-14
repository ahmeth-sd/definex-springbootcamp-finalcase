package com.patikadev.finalcase.exception;

public class AttachmentNotFoundException extends RuntimeException {
    public AttachmentNotFoundException(Long id) {
        super("Attachment with id " + id + " not found");
    }
}