package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.service.TaskService;
import com.patikadev.finalcase.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;

    private Task task;
    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTitle("Test Task");

        user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    void testGetAllTasks() {
        when(taskService.getAllTasks()).thenReturn(List.of(task));

        List<Task> result = taskController.getAllTasks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testCreateTask() {
        task.setId(1L); // Ensure the task has an ID
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> result = taskController.createTask(task);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void testGetTaskById() {
        task.setId(1L); // Ensure the task has an ID
        when(taskService.getTaskById(1L)).thenReturn(task);

        ResponseEntity<Task> result = taskController.getTaskById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testUpdateTask() {
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        task.setId(1L); // Ensure the task has an ID
        when(taskService.updateTask(anyLong(), any(Task.class), any(Users.class), anyLong())).thenReturn(task);

        ResponseEntity<Task> result = taskController.updateTask(1L, task, 1L);
        assertNotNull(result);
        assertEquals(1L, result.getBody().getId());
        verify(taskService, times(1)).updateTask(anyLong(), any(Task.class), any(Users.class), anyLong());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> result = taskController.deleteTask(1L);
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(taskService, times(1)).deleteTask(1L);
    }
}