package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Task;
import com.patikadev.finalcase.entity.TeamMember;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.InvalidTaskStateException;
import com.patikadev.finalcase.exception.TaskNotFoundException;
import com.patikadev.finalcase.exception.UnauthorizedException;
import com.patikadev.finalcase.repository.TaskRepository;
import com.patikadev.finalcase.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private Users teamMember;
    private Users teamLeader;
    private TeamMember teamMemberEntity;
    private TeamMember teamLeaderEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setState(Task.State.IN_DEVELOPMENT);

        teamMember = new Users();
        teamMember.setId(1L);

        teamLeader = new Users();
        teamLeader.setId(2L);

        teamMemberEntity = new TeamMember();
        teamMemberEntity.setUser(teamMember);
        teamMemberEntity.setRole(TeamMember.Role.MEMBER);

        teamLeaderEntity = new TeamMember();
        teamLeaderEntity.setUser(teamLeader);
        teamLeaderEntity.setRole(TeamMember.Role.TEAM_LEADER);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(new Task(), new Task()));

        var result = taskService.getAllTasks();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testCreateTask() {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        var result = taskService.createTask(newTask);
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);
        assertTrue(task.isDeleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_InvalidStateChange_Completed() {
        task.setState(Task.State.COMPLETED);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.IN_DEVELOPMENT);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_CancelledWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.CANCELLED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_BlockedWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BLOCKED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_UnauthorizedTitleChange() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setTitle("New Title");

        assertThrows(UnauthorizedException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void testUpdateTask_HappyPath() {
        task.setState(Task.State.BACKLOG);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.IN_ANALYSIS);
        taskService.updateTask(1L, newTask, teamLeader, 1L);

        newTask.setState(Task.State.IN_DEVELOPMENT);
        taskService.updateTask(1L, newTask, teamLeader, 1L);

        newTask.setState(Task.State.COMPLETED);
        taskService.updateTask(1L, newTask, teamLeader, 1L);

        verify(taskRepository, times(3)).save(task);
    }

    @Test
    void testUpdateTask_CancelPath() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.CANCELLED);
        newTask.setReason("Valid reason");

        taskService.updateTask(1L, newTask, teamLeader, 1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_CancelPathWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.CANCELLED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_BlockedPath_InAnalysis() {
        task.setState(Task.State.IN_ANALYSIS);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BLOCKED);
        newTask.setReason("Valid reason");

        taskService.updateTask(1L, newTask, teamLeader, 1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_BlockedPath_InDevelopment() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BLOCKED);
        newTask.setReason("Valid reason");

        taskService.updateTask(1L, newTask, teamLeader, 1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_BlockedPathWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BLOCKED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_CompletedCannotBeChanged() {
        task.setState(Task.State.COMPLETED);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.IN_DEVELOPMENT);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_TeamMemberCannotChangeTitleOrDescription() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setTitle("New Title");
        newTask.setDescription("New Description");

        assertThrows(UnauthorizedException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }

    @Test
    void testUpdateTask_TeamMemberCanChangeState() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.COMPLETED);

        var result = taskService.updateTask(1L, newTask, teamMember, 1L);
        assertNotNull(result);
        assertEquals(Task.State.COMPLETED, result.getState());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_TeamLeaderCanChangeTitleAndDescription() {
        task.setState(Task.State.IN_ANALYSIS);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setTitle("New Title");
        newTask.setDescription("New Description");
        newTask.setState(Task.State.IN_DEVELOPMENT);

        var result = taskService.updateTask(1L, newTask, teamLeader, 1L);
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_InvalidStateChange_Backlog() {
        task.setState(Task.State.BACKLOG);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.COMPLETED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_InvalidStateChange_InAnalysis() {
        task.setState(Task.State.IN_ANALYSIS);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.COMPLETED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_InvalidStateChange_InDevelopment() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BACKLOG);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testUpdateTask_TeamMemberCannotChangeStateToCancelledWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.CANCELLED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }

    @Test
    void testUpdateTask_TeamMemberCannotChangeStateToBlockedWithoutReason() {
        task.setState(Task.State.IN_DEVELOPMENT);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setState(Task.State.BLOCKED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }

    @Test
    void testValidateTaskStateChange_InvalidTransition() {
        task.setState(Task.State.BACKLOG);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamLeaderEntity));
        Task newTask = new Task();
        newTask.setState(Task.State.COMPLETED);

        assertThrows(InvalidTaskStateException.class, () -> taskService.updateTask(1L, newTask, teamLeader, 1L));
    }

    @Test
    void testValidateUserPermissions_NotMember() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Task newTask = new Task();
        newTask.setTitle("New Title");

        assertThrows(UnauthorizedException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }

    @Test
    void testValidateUserPermissions_NotAuthorized() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(teamMemberRepository.findByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(teamMemberEntity));

        Task newTask = new Task();
        newTask.setTitle("New Title");

        assertThrows(UnauthorizedException.class, () -> taskService.updateTask(1L, newTask, teamMember, 1L));
    }
}