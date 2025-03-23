package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.service.TaskService;
import com.patikadev.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get a task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Validate assignee_id
        Users assignee = userService.getUserById(task.getAssignedUser().getId());
        task.setAssignedUser(assignee);

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @Operation(summary = "Update an existing task")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails, @RequestParam Long projectId) {
        Users currentUser = getCurrentUser();
        Task updatedTask = taskService.updateTask(id, taskDetails, currentUser, projectId);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Delete a task by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    private Users getCurrentUser() {
        // Implement this method to retrieve the current user from the security context or session
        return userService.getUserByEmail("current_user_email@example.com"); // Replace with actual user retrieval logic
    }
}