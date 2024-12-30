package com.example.demo.services;

import com.example.demo.model.Bot;


public interface BotService {
    Bot findByToken(String token);

    Bot createBot(Bot bot);

    void deleteBot(Bot bot);
}