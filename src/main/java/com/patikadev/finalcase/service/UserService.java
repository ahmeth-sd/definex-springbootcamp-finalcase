package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.User;
import com.patikadev.finalcase.service.UserService;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
