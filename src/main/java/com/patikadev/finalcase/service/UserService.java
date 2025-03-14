package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Users;

import java.util.List;

public interface UserService {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByEmail(String email);
    Users createUser(Users user);
    Users updateUser(Long id, Users user);
    void deleteUser(Long id);
}
