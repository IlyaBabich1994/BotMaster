package com.example.demo.services;

import com.example.demo.model.Button;
import com.example.demo.repository.ButtonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ButtonServiceImpl implements ButtonService {
    private static final Logger logger = LoggerFactory.getLogger(ButtonServiceImpl.class);
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
        if (button == null) {
            throw new IllegalArgumentException("Button must not be null");
        } else {
            buttonRepository.save(button);
        }
    }

    @Override
    public void removeButton(Button button) {
        if (button == null) {
            logger.error("Button is null");
            throw new IllegalArgumentException("Button must not be null");
        }
        if (!buttonRepository.existsById(button.getId())) {
            logger.error("Button with ID {} not found", button.getId());
            throw new IllegalArgumentException("Button not found");
        } else {
            buttonRepository.delete(button);
        }
    }
}
