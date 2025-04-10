package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.Department;
import com.patikadev.finalcase.entity.Users;
import com.patikadev.finalcase.exception.UserEmailNotFoundException;
import com.patikadev.finalcase.exception.UserNotFoundException;
import com.patikadev.finalcase.repository.UserRepository;
import com.patikadev.finalcase.service.DepartmentService;
import com.patikadev.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentService departmentService;



    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, DepartmentService departmentService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.departmentService = departmentService;
    }

    @Override
    public List<Users> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        logger.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Users getUserByEmail(String email) {
        logger.info("Getting user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));
    }

    @Override
    public Users createUser(Users user) {
        logger.info("Creating new user with email: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users updateUser(Long id, Users userDetails) {
        logger.info("Updating user with id: {}", id);
        Users user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void setDepartmentForUser(Long userId, Long departmentId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Department department = departmentService.getDepartmentById(departmentId);
        user.setDepartment(department);
        userRepository.save(user);
    }

}
