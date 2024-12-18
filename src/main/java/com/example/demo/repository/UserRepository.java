package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(Long telegramId);
    @Query("update User u set u.isBlocked = true where u.id = ?1")
    void blockUser(Long userId);

    @Query("update User u set u.isBlocked = false where u.id = ?1")
    void unblockUser(Long userId);
}