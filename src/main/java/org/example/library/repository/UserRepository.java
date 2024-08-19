package org.example.library.repository;

import org.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String code);
    Optional<List<User>> findAllByEnabledFalseAndCreationTimeBefore(LocalDateTime cutOffTime);
}
