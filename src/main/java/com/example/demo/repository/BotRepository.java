package com.example.demo.repository;

import com.example.demo.model.Bot;

import java.util.ArrayList;
import java.util.List;

public class BotRepository {
    private List<Bot> bots = new ArrayList<>();

    public Bot findByToken(String token) {
        for (Bot bot : bots) {
            if (bot.getToken().equals(token)) {
                return bot;
            }
        }
        return null;
    }
}
