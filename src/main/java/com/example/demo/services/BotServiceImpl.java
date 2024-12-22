package com.example.demo.services;

import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotServiceImpl implements BotService {
    @Autowired
    private final BotRepository botRepository;

    public BotServiceImpl(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    @Override
    public Bot findByToken(String token) {
        return botRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Bot not found")
        );
    }

    @Override
    public void createBot(Bot bot) {
        botRepository.save(bot);
    }

    @Override
    public void deleteBot(Bot bot) {
        botRepository.delete(bot);
    }
}