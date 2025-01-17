package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.repository.BotRepository;
import com.example.demo.repository.FilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BotControllerTest {

    @InjectMocks
    private BotController botController;

    @Mock
    private BotRepository botRepository;

    @Mock
    private FilterRepository filterRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBot_Success() {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("Test Bot");
        botRequest.setToken("test-token");
        botRequest.setWelcomeMessage("Welcome!");
        botRequest.setFilters(Collections.singletonList(new FilterRequest(".*", "ALLOW")));

        when(botRepository.existsByName("Test Bot")).thenReturn(false);
        when(botRepository.save(any(Bot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<BotResponse> response = botController.addBot(botRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Bot", response.getBody().getName());
        verify(botRepository, times(1)).save(any(Bot.class));
    }

    @Test
    public void testAddBot_Conflict() {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("Existing Bot");

        when(botRepository.existsByName("Existing Bot")).thenReturn(true);

        ResponseEntity<BotResponse> response = botController.addBot(botRequest);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(botRepository, never()).save(any(Bot.class));
    }

    @Test
    public void testAddBot_Exception() {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("Test Bot");
        botRequest.setToken("test-token");
        botRequest.setWelcomeMessage("Welcome!");

        when(botRepository.existsByName("Test Bot")).thenReturn(false);
        when(botRepository.save(any(Bot.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<BotResponse> response = botController.addBot(botRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}