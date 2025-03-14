package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.entity.Users;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task taskDetails, Users user);
    void deleteTask(Long id);
}