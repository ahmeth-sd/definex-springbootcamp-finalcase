package com.patikadev.finalcase.service.impl;

import com.patikadev.finalcase.entity.User;
import com.patikadev.finalcase.exception.UserEmailNotFoundException;
import com.patikadev.finalcase.exception.UserNotFoundException;
import com.patikadev.finalcase.repository.UserRepository;
import com.patikadev.finalcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        logger.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info("Getting user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));
    }

    @Override
    public User createUser(User user) {
        logger.info("Creating new user with email: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with id: {}", id);
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
}
