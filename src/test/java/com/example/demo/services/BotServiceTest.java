package com.example.demo.services;

import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.example.demo.util.Creater.createBot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BotServiceTest {
    private final BotRepository botRepository = Mockito.mock(BotRepository.class);
    private final BotService botService = new BotServiceImpl(botRepository);

    @Test
    public void testFindByTokenShouldReturnBot() {
        String token = "token";
        Bot expectedResult = createBot();
        when(botRepository.findByToken(token)).thenReturn(Optional.of(expectedResult));

        Bot result = botService.findByToken(token);

        verify(botRepository, only()).findByToken(token);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindByTokenShouldThrowException() {
        String token = "token";
        when(botRepository.findByToken(token)).thenThrow(new RuntimeException("Bot not found"));

        assertThrows(RuntimeException.class, () -> botService.findByToken(token));
        verify(botRepository, only()).findByToken(any());
    }

    @Test
    public void testCreateBot() {
        Bot bot = createBot();
        botService.createBot(bot);
        verify(botRepository, only()).save(bot);
    }

    @Test
    public void testDeleteBot() {
        Bot bot = createBot();
        botService.deleteBot(bot);
        verify(botRepository, only()).delete(bot);
    }

}