package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.UserEmailNotFoundException;
import com.patikadev.finalcase.exception.UserNotFoundException;
import com.patikadev.finalcase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;

    private Users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new Users(), new Users()));

        var result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserEmailNotFoundException.class, () -> userService.getUserByEmail("john.doe@example.com"));
    }

    @Test
    void testGetUserByEmail_Found() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var result = userService.getUserByEmail("john.doe@example.com");
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testCreateUser() {
        Users newUser = new Users();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane.doe@example.com");
        newUser.setPassword("password123");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(newUser);

        var result = userService.createUser(newUser);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Users updatedUser = new Users();
        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updatedUser));
    }

    @Test
    void testUpdateUser_Found() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users updatedUser = new Users();
        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");

        var result = userService.updateUser(1L, updatedUser);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}