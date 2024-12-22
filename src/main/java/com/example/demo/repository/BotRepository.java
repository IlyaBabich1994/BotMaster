package com.example.demo.repository;

import com.example.demo.model.Bot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotRepository extends CrudRepository<Bot, Long> {
    Optional<Bot> findByToken(String token);
}
