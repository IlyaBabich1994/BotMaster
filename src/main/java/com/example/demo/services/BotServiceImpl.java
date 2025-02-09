package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotUpdateRequest;
import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BotServiceImpl implements BotService {
    private final BotRepository botRepository;
    @Autowired
    private FilterService filterService;
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
    @Transactional
    public void updateBot(Bot existing, BotUpdateRequest update) {
        if (update.getName() != null) {
            existing.setName(update.getName());
        }
        if (update.getWelcomeMessage() != null) {
            existing.setWelcomeMessage(update.getWelcomeMessage());
        }
        filterService.updateFiltersForBot(existing, update.getFilters());
    }
    @Override
    public Optional<Bot> findById(Long id) {
        return Optional.ofNullable(botRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Bot not found")
        ));
    }

    public boolean existsByNameAndUser(String name, Long userId) {
        return botRepository.existsByNameAndUserId(name, userId);
    }
    @Override
    public List<Bot> findAll() {
        return botRepository.findAll();
    }
}