package com.example.demo.util;

import com.example.demo.model.Bot;
import com.example.demo.model.Button;
import com.example.demo.model.Filter;
import com.example.demo.model.User;

import java.util.*;

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
    public static Button createButton(
            Long id,
            Long botId,
            String label,
            String callbackData
    ) {
        return Button.builder()
                .id(id)
                .botId(botId)
                .label(label)
                .callbackData(callbackData)
                .build();
    }
    public static Button createButton() {
        return createButton(
                1L,
                1L,
                "label",
                "callbackdata"
        );
    }
    public static Filter createFilter(
            Long id,
            Long botId,
            String patern,
            List<String> action
    ) {
        return Filter.builder()
                .id(id)
                .botId(botId)
                .patern(patern)
                .action(action)
                .build();
    }
    public static Filter createFilter() {
        String action1 = "action1";
        String action2 = "action2";
        List<String> actions = Arrays.asList(action1,action2);
        return createFilter(
                1L,
                1L,
                "label",
                actions
        );
    }
    public static User createUser(
            Long id,
            Long botId,
            Long telegramId,
            boolean status,
            boolean state
    ){
        return User.builder()
                .id(id)
                .botId(botId)
                .telegramId(telegramId)
                .status(status)
                .state(state)
                .build();
    }
    public static User createUser(){
        return createUser(
                1L,
                1L,
                1L,
                true,
                true
        );
    }
}
