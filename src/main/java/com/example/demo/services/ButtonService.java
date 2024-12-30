package com.example.demo.services;

import com.example.demo.model.Button;

import java.util.List;

public interface ButtonService {
    List<Button> findAllByBotId(Long botId);

    void addButton(Button button);

    void removeButton(Button button);
}
