package com.example.demo.mapper;

import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.BotUpdateRequest;
import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.FilterResponse;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BotMapper {
    public static Bot toBot(BotRequest botRequest) {
        if (botRequest == null) {
            throw new IllegalArgumentException("BotRequest cannot be null");
        }
        return Bot.builder()
                .name(botRequest.getName())
                .token(botRequest.getToken())
                .welcomeMessage(botRequest.getWelcomeMessage())
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
                bot.getWelcomeMessage(),
                mapFilters(bot.getFilters())
        );
    }

    public static void updateBotFromRequest(BotUpdateRequest updateRequest, Bot bot) {
        if (updateRequest.getName() != null) {
            bot.setName(updateRequest.getName());
        }
        if (updateRequest.getWelcomeMessage() != null) {
            bot.setWelcomeMessage(updateRequest.getWelcomeMessage());
        }
    }

    public static Filter toFilter(FilterRequest filterRequest, Bot bot) {
        return Filter.builder()
                .pattern(filterRequest.getPattern())
                .action(Collections.singletonList(filterRequest.getAction()))
                .bot(bot)
                .build();
    }

    public static FilterResponse toFilterResponse(Filter filter) {
        return new FilterResponse(
                filter.getId(),
                filter.getPattern(),
                filter.getAction()
        );
    }

    private static List<FilterResponse> mapFilters(Collection<Filter> filters) {
        return filters.stream()
                .map(BotMapper::toFilterResponse)
                .collect(Collectors.toList());
    }
}

