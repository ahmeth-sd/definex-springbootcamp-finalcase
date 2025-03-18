package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.InvalidTaskStateException;
import com.patikadev.finalcase.exception.TaskNotFoundException;
import com.patikadev.finalcase.exception.UnauthorizedException;
import com.patikadev.finalcase.repository.TaskRepository;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import com.patikadev.finalcase.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

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
    public Task createTask(Task task) {
        logger.info("Creating new task with title: {}", task.getTitle());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task taskDetails, Users user, Long projectId) {
        logger.info("Updating task with id: {}", id);
        Task task = getTaskById(id);
        validateUserPermissions(task, user, taskDetails, projectId);
        validateTaskStateChange(task, taskDetails);
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
    public void deleteTask(Long id) {
        logger.info("Deleting task with id: {}", id);
        Task task = getTaskById(id);
        task.setDeleted(true);
        taskRepository.save(task);
    }

    private void validateTaskStateChange(Task currentTask, Task newTask) {
        if (currentTask.getState() == Task.State.COMPLETED) {
            throw new InvalidTaskStateException("Completed tasks cannot be changed.");
        }

        if (newTask.getState() == Task.State.CANCELLED && newTask.getReason() == null) {
            throw new InvalidTaskStateException("Cancellation reason must be provided.");
        }

        if (newTask.getState() == Task.State.BLOCKED && newTask.getReason() == null) {
            throw new InvalidTaskStateException("Blocking reason must be provided.");
        }

        if (currentTask.getState() == Task.State.BACKLOG && newTask.getState() != Task.State.IN_ANALYSIS) {
            throw new InvalidTaskStateException("Invalid state transition from Backlog.");
        }

        if (currentTask.getState() == Task.State.IN_ANALYSIS &&
                (newTask.getState() != Task.State.IN_DEVELOPMENT && newTask.getState() != Task.State.BLOCKED && newTask.getState() != Task.State.CANCELLED)) {
            throw new InvalidTaskStateException("Invalid state transition from In Analysis.");
        }

        if (currentTask.getState() == Task.State.IN_DEVELOPMENT &&
                (newTask.getState() != Task.State.COMPLETED && newTask.getState() != Task.State.BLOCKED && newTask.getState() != Task.State.CANCELLED)) {
            throw new InvalidTaskStateException("Invalid state transition from In Development.");
        }
    }

    private void validateUserPermissions(Task task, Users user, Task taskDetails, Long projectId) {
        TeamMember teamMember = teamMemberRepository.findByProjectIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new UnauthorizedException("User is not a member of the project."));

        if (teamMember.getRole() != TeamMember.Role.TEAM_LEADER && teamMember.getRole() != TeamMember.Role.PROJECT_MANAGER) {
            boolean isTitleChanged = taskDetails.getTitle() != null && !task.getTitle().equals(taskDetails.getTitle());
            boolean isDescriptionChanged = taskDetails.getDescription() != null &&
                    (task.getDescription() == null || !task.getDescription().equals(taskDetails.getDescription()));
            if (isTitleChanged || isDescriptionChanged) {
                throw new UnauthorizedException("Only Team Leader or Project Manager can change the title and description.");
            }
        }
    }
}