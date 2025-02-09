package com.example.demo.services;

import com.example.demo.model.Bot;

import java.util.List;


public interface BotService {
    Bot findByToken(String token);

    Bot createBot(Bot bot);

    void deleteBot(Bot bot);

    void deleteBotById(Long id);

    Bot findById(Long id);
    Bot updateBot(Bot bot);
    boolean existsByName(String name);
    List<Bot> findAll();
}