package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotUpdateRequest;
import com.example.demo.model.Bot;

import java.util.Optional;


public interface BotService {
    Bot findByToken(String token);

    Bot createBot(Bot bot);

    void deleteBot(Bot bot);
    Optional<Bot> findById(Long id);
    void updateBot(Bot existing, BotUpdateRequest update);
    public boolean existsByNameAndUser(String name, Long userId);
}