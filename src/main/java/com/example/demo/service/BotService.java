package com.example.demo.service;

import com.example.demo.model.Bot;

import java.util.Optional;

public interface BotService {
    Bot findByToken(String token);
    void createBot(Bot bot);
    void deleteBot(Bot bot);
}
