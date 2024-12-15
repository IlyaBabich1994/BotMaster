package com.example.demo.Services;

import com.example.demo.model.Bot;

public interface BotService {
    Bot createBot(Bot bot); // создание по типу сохранения
    Bot updateBot(Bot bot);  // передаём нового бота на перезапись уже имеющегося
    String deleteBot(Bot bot); // удаляем ненужного бота (можно найти по "bot.getId")
}
