package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotListResponse;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.model.BotCategory;
import com.example.demo.model.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc = MockMvcBuilders.standaloneSetup(botController)
                .build();

    }

    @Test
    public void testAddBot_Success() throws Exception {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("MyBot");
        botRequest.setToken("123456789");
        botRequest.setCategory(BotCategory.MODERATION);
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
        botRequest.setCategory(BotCategory.MODERATION);
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

        doNothing().when(botService).deleteBotById(botId);

        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Bot successfully deleted"));
    }

    @Test
    public void deleteBot_NotFound() throws Exception {
        Long botId = 1L;

        doThrow(new NoSuchElementException("Bot not found")).when(botService).deleteBotById(botId);

        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Bot not found"));
    }

    @Test
    public void deleteBot_InternalServerError() throws Exception {
        Long botId = 1L;
        doThrow(new RuntimeException("Some error")).when(botService).deleteBotById(botId);
        mockMvc.perform(delete("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred"));
    }

    @Test
    void updateBot_NameConflict() throws Exception {
        Long botId = 1L;
        Bot existingBot = Bot.builder()
                .id(botId)
                .name("OldName")
                .token("conflict-token")
                .category(BotCategory.SUPPORT)
                .welcomeMessage("Message")
                .status("ACTIVE")
                .createdAt(new Date())
                .build();

        when(botService.findById(botId)).thenReturn(existingBot);
        when(botService.existsByName("NewName")).thenReturn(true);

        String requestBody = """
                {
                    "name": "NewName",
                    "category": "SUPPORT",
                    "welcomeMessage": "Updated message"
                }""";

        mockMvc.perform(put("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string("Bot name already exists"));
    }

    @Test
    void updateBot_NotFound() throws Exception {
        String validRequest = """
                {
                    "name": "TempName",
                    "category": "SUPPORT",
                    "welcomeMessage": "Hello"
                }""";

        when(botService.findById(99L)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put("/api/v1/bots/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBot_InternalServerError() throws Exception {
        Long botId = 1L;
        Bot existingBot = Bot.builder()
                .id(botId)
                .name("TestBot")
                .token("test-token")
                .category(BotCategory.SUPPORT)
                .status("ACTIVE")
                .createdAt(new Date())
                .build();

        when(botService.findById(botId)).thenReturn(existingBot);
        doThrow(new RuntimeException("DB error")).when(filterService).deleteAllByBotId(anyLong());

        String requestBody = """
                {
                    "name": "Test",
                    "category": "SUPPORT",
                    "welcomeMessage": "Test"
                }""";

        mockMvc.perform(put("/api/v1/bots/{id}", botId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllBots_shouldReturnListOfBots() {
        List<Bot> mockBots = Arrays.asList(
                new Bot(1L, "ChatModerator", "token1", "Welcome", BotCategory.MODERATION,
                        new Date(), null, "ACTIVE"),
                new Bot(2L, "SupportBot", "token2", "Hi", BotCategory.SUPPORT,
                        new Date(), null, "INACTIVE")
        );

        when(botService.findAll()).thenReturn(mockBots);

        ResponseEntity<List<BotListResponse>> response = botController.getAllBots();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        BotListResponse firstBot = response.getBody().get(0);
        assertEquals(1L, firstBot.getId());
        assertEquals("ChatModerator", firstBot.getName());
        assertEquals("ACTIVE", firstBot.getStatus());
    }

    @Test
    void getAllBots_shouldReturnEmptyList() {
        when(botService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<BotListResponse>> response = botController.getAllBots();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
