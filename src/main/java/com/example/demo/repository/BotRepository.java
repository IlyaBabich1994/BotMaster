package com.example.demo.repository;

import com.example.demo.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotRepository extends JpaRepository<Bot, Long> {
    Optional<Bot> findByToken(String token);
    boolean existsByName(String name);
}
