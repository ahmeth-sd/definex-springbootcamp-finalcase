package com.patikadev.finalcase.controller;

import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        List<Users> result = userController.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(user);

        Users result = userController.getUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(anyLong(), any(Users.class))).thenReturn(user);

        Users result = userController.updateUser(1L, user);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userService, times(1)).updateUser(anyLong(), any(Users.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        userController.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }
}