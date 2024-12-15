package com.example.demo.repository;

import com.example.demo.model.Button;
import com.example.demo.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class ButtonRepository {
    private List<Button> buttons = new ArrayList<>();
    public List<Button> resultButton = new ArrayList<>();

    public List<Button> findAllBuBotId (Long botId){
        for (Button button:buttons){
            if (button.getBotId()==botId){
                resultButton.add(button);
            }
        }
        return buttons;
    }
}
