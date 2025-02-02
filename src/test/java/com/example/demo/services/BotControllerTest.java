package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class BotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BotService botService;

    @Mock
    private FilterService filterService;

    @InjectMocks
    private BotController botController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(botController).build();
    }

    @Test
    public void testAddBot_Success() throws Exception {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("MyBot");
        botRequest.setToken("123456789");
        botRequest.setCategory("General");
        botRequest.setWelcomeMessage("Welcome to MyBot!");
        botRequest.setFilters(Arrays.asList(new FilterRequest()));

        Bot bot = new Bot();
        bot.setId(1L);
        bot.setName("MyBot");
        bot.setCreatedAt(new Date());
        bot.setStatus("ACTIVE");

        when(botService.createBot(any(Bot.class))).thenReturn(bot);

        mockMvc.perform(post("/api/v1/bots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(botRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("MyBot"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        ArgumentCaptor<Bot> botCaptor = forClass(Bot.class);
        verify(botService, times(1)).createBot(botCaptor.capture());

        Bot capturedBot = botCaptor.getValue();
        assertEquals("MyBot", capturedBot.getName());
        assertEquals("123456789", capturedBot.getToken());
        assertEquals("ACTIVE", capturedBot.getStatus());
    }

    @Test
    public void testAddBot_InternalServerError() throws Exception {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("MyBot");
        botRequest.setToken("myToken");
        botRequest.setCategory("myCategory");
        when(botService.createBot(any(Bot.class))).thenThrow(new RuntimeException("Database error"));
        mockMvc.perform(post("/api/v1/bots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(botRequest)))
                .andExpect(status().isInternalServerError());
        verify(botService, times(1)).createBot(any(Bot.class));
        verify(filterService, times(0)).addFilter(any(Filter.class));
    }

    @Test
    public void deleteBot_Success() throws Exception {
        Long botId = 1L;
        Bot bot = new Bot();

        when(botService.findById(botId)).thenReturn(bot);
        doNothing().when(botService).deleteBot(bot);

        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bot successfully deleted"));
    }

    @Test
    public void deleteBot_NotFound() throws Exception {
        Long botId = 1L;

        when(botService.findById(botId)).thenThrow(new NoSuchElementException("Bot not found"));

        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Bot not found"));
    }

    @Test
    public void deleteBot_InternalServerError() throws Exception {
        Long botId = 1L;
        Bot bot = new Bot();

        when(botService.findById(botId)).thenReturn(bot);
        doThrow(new RuntimeException("Some error")).when(botService).deleteBot(bot);

        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An error occurred"));
    }
}