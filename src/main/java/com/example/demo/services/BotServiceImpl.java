package com.example.demo.services;

import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotServiceImpl implements BotService {
    private final BotRepository botRepository;
    @Autowired
    public BotServiceImpl(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    @Override
    public Bot findByToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        } else {
            return botRepository.findByToken(token).orElseThrow(
                    () -> new RuntimeException("Bot not found")
            );
        }
    }

    @Override
    public Bot createBot(Bot bot) {
        botRepository.save(bot);
        return bot;
    }

    @Override
    public void deleteBot(Bot bot) {
        botRepository.delete(bot);
    }
}