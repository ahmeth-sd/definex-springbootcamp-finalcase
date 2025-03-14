package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Özel sorgu metodu
}
