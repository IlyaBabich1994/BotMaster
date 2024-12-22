package com.example.demo.util;

import com.example.demo.model.Bot;

import java.util.Calendar;
import java.util.Date;

public class Creater {
    public static Bot createBot(
            Long id,
            String name,
            String token,
            String welcomeMessage,
            Date createdAt
    ) {
        return Bot.builder()
                .id(id)
                .name(name)
                .token(token)
                .welcomeMessage(welcomeMessage)
                .createdAt(createdAt)
                .build();
    }

    public static Bot createBot(
    ) {
        return createBot(
                1L,
                "name",
                "token",
                "welcomeMessage",
                new Date(Calendar.getInstance().getTimeInMillis()
                )
        );
    }
}
