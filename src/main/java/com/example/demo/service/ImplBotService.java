package com.example.demo.service;

import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImplBotService implements BotService{
    private final BotRepository botRepository;
    public ImplBotService(BotRepository botRepository) {
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
