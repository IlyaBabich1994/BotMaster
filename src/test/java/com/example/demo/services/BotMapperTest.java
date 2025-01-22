package com.example.demo.services;

import com.example.demo.dto.BotRequest;
import com.example.demo.dto.FilterRequest;
import com.example.demo.mapper.BotMapper;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotMapperTest {

    @Test
    public void testToBot_ValidRequest() {
        BotRequest botRequest = new BotRequest();
        botRequest.setName("MyBot");
        botRequest.setToken("myToken");
        botRequest.setWelcomeMessage("Welcome to MyBot");

        Bot bot = BotMapper.toBot(botRequest);
        assertNotNull(bot);
        assertEquals("MyBot", bot.getName());
        assertEquals("myToken", bot.getToken());
        assertEquals("Welcome to MyBot", bot.getWelcomeMessage());
        assertNotNull(bot.getCreatedAt());
        assertEquals("ACTIVE", bot.getStatus());
    }

    @Test
    public void testToBot_NullRequest() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            BotMapper.toBot(null);
        });
        assertEquals("BotRequest cannot be null", thrown.getMessage());
    }

    @Test
    public void testToFilters_NullFilterRequests() {
        Bot bot = new Bot();
        List<Filter> filters = BotMapper.toFilters(bot, null);
        assertNotNull(filters);
        assertTrue(filters.isEmpty());
    }

    @Test
    public void testToFilters_ValidRequests() {
        Bot bot = new Bot();
        FilterRequest filterRequest1 = new FilterRequest();
        filterRequest1.setPattern("pattern1");
        filterRequest1.setAction("action1");

        FilterRequest filterRequest2 = new FilterRequest();
        filterRequest2.setPattern("pattern2");
        filterRequest2.setAction("action2");

        List<FilterRequest> filterRequests = List.of(filterRequest1, filterRequest2);
        List<Filter> filters = BotMapper.toFilters(bot, filterRequests);

        assertNotNull(filters);
        assertEquals(2, filters.size());
        assertEquals("pattern1", filters.get(0).getPattern());
        assertEquals(Collections.singletonList("action1"), filters.get(0).getAction());
        assertEquals(bot, filters.get(0).getBot());
        assertEquals("pattern2", filters.get(1).getPattern());
        assertEquals(Collections.singletonList("action2"), filters.get(1).getAction());
        assertEquals(bot, filters.get(1).getBot());
    }
}