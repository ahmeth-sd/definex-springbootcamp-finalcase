package com.patikadev.finalcase.exception;

public class TeamMemberNotFoundException extends RuntimeException {
    public TeamMemberNotFoundException(Long id) {
        super("Team member with id " + id + " not found");
    }
}