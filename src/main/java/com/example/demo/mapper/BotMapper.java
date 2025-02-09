package com.example.demo.mapper;

import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BotMapper {
    public static Bot toBot(BotRequest botRequest) {
        if (botRequest == null) {
            throw new IllegalArgumentException("BotRequest cannot be null");
        }
        return Bot.builder()
                .name(botRequest.getName())
                .token(botRequest.getToken())
                .welcomeMessage(botRequest.getWelcomeMessage())
                .category(botRequest.getCategory())
                .createdAt(new Date())
                .status("ACTIVE")
                .build();
    }

    public static List<Filter> toFilters(Bot bot, List<FilterRequest> filterRequests) {
        if (filterRequests == null) {
            return Collections.emptyList();
        }

        List<Filter> filters = new ArrayList<>();
        for (FilterRequest filterRequest : filterRequests) {
            Filter filter = Filter.builder()
                    .pattern(filterRequest.getPattern())
                    .action(Collections.singletonList(filterRequest.getAction()))
                    .bot(bot)
                    .build();
            filters.add(filter);
        }
        return filters;
    }
    public static BotResponse toResponse(Bot bot) {
        return new BotResponse(
                bot.getId(),
                bot.getName(),
                bot.getFilters(),
                bot.getStatus(),
                bot.getCreatedAt(),
                bot.getCategory()
        );
    }
}

