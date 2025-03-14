package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.exception.TaskNotFoundException;
import com.patikadev.finalcase.repository.TaskRepository;
import com.patikadev.finalcase.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        logger.info("Getting task by id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    @Transactional
    public Task createTask(Task task) {
        logger.info("Creating new task with title: {}", task.getTitle());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        logger.info("Updating task with id: {}", id);
        Task task = getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setAcceptanceCriteria(taskDetails.getAcceptanceCriteria());
        task.setPriority(taskDetails.getPriority());
        task.setState(taskDetails.getState());
        task.setReason(taskDetails.getReason());
        task.setAssignedUser(taskDetails.getAssignedUser());
        task.setProject(taskDetails.getProject());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        logger.info("Deleting task with id: {}", id);
        taskRepository.deleteById(id);
    }
}