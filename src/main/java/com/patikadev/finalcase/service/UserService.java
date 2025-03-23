package com.patikadev.finalcase.service;

import com.patikadev.finalcase.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByEmail(String email);
    Users createUser(Users user);
    Users updateUser(Long id, Users user);
    void deleteUser(Long id);
    void setDepartmentForUser(Long userId, Long departmentId);

}
