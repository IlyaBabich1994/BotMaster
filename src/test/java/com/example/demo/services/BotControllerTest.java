package com.example.demo.services;

import com.example.demo.controlers.BotController;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.BotUpdateRequest;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Autowired
    private ObjectMapper objectMapper;
    private final Long testBotId = 1L;
    private final Long ownerId = 100L;

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
    void updateBot_ValidRequest_NoNameChange_ReturnsOk() throws Exception {
        BotUpdateRequest request = new BotUpdateRequest(null, "New welcome");
        Bot existingBot = createTestBot("Old Name", ownerId);
        BotResponse response = new BotResponse(testBotId, "Old Name", "New welcome", List.of());
        when(botService.findById(testBotId)).thenReturn(Optional.of(existingBot));
        when(botService.existsByNameAndUser(anyString(), anyLong())).thenReturn(false);
        mockMvc.perform(put("/bots/{id}", testBotId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.welcomeMessage").value("New welcome"));

        verify(botService).updateBot(existingBot, request);
    }

    @Test
    void updateBot_DuplicateName_ReturnsConflict() throws Exception {
        BotUpdateRequest request = new BotUpdateRequest("Existing", null);
        Bot existingBot = createTestBot("Old Name", ownerId);
        when(botService.findById(testBotId)).thenReturn(Optional.of(existingBot));
        when(botService.existsByNameAndUser("Existing", ownerId)).thenReturn(true);
        mockMvc.perform(put("/bots/{id}", testBotId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("")));
    }
    @Test
    void updateBot_InvalidName_ReturnsBadRequest() throws Exception {
        BotUpdateRequest request = new BotUpdateRequest("X", null);
        mockMvc.perform(put("/bots/{id}", testBotId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBot_NotExists_ReturnsNotFound() throws Exception {
        when(botService.findById(testBotId)).thenReturn(Optional.empty());
        mockMvc.perform(put("/bots/{id}", testBotId)
                        .content(objectMapper.writeValueAsString(new BotUpdateRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    private Bot createTestBot(String name, Long ownerId) {
        User owner = new User();
        owner.setId(ownerId);
        Bot bot = new Bot();
        bot.setId(testBotId);
        bot.setName(name);
        bot.setOwner(owner);
        return bot;
    }
}