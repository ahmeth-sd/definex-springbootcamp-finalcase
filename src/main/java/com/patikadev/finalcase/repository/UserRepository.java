package com.patikadev.finalcase.repository;

import com.patikadev.finalcase.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);  // Özel sorgu metodu
}
