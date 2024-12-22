package com.example.demo.services;

import com.example.demo.model.Button;
import com.example.demo.repository.ButtonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ButtonServiceImpl implements ButtonService {
    private final ButtonRepository buttonRepository;

    @Autowired
    public ButtonServiceImpl(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    @Override
    public List<Button> findAllByBotId(Long botId) {
        return buttonRepository.findAllByBotId(botId);
    }

    @Override
    public void addButton(Button button) {
        buttonRepository.save(button);
    }

    @Override
    public void removeButton(Button button) {
        buttonRepository.delete(button);
    }
}
