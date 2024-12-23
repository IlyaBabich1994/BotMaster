package com.example.demo.services;

import com.example.demo.model.Button;
import com.example.demo.repository.ButtonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.util.Creater.createButton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ButtonServiceImplTest {
    private final ButtonRepository buttonRepository = Mockito.mock(ButtonRepository.class);
    private final ButtonService buttonService = new ButtonServiceImpl(buttonRepository);

    @Test
    public void testFindAllByBotIdShouldReturnListOfButtons() {
        Long botId = 1L;
        Button button1 = new Button();
        Button button2 = new Button();
        List<Button> expectedButtons = Arrays.asList(button1, button2);
        when(buttonRepository.findAllByBotId(botId)).thenReturn(expectedButtons);
        List<Button> actualButtons = buttonService.findAllByBotId(botId);
        assertEquals(expectedButtons, actualButtons);
        verify(buttonRepository, times(1)).findAllByBotId(botId);
    }

    @Test
    public void testCreateButton() {
        Button button = createButton();
        buttonService.addButton(button);
        verify(buttonRepository, only()).save(button);
    }

    @Test
    public void testDeleteButton() {
        Button button = createButton();
        buttonService.removeButton(button);
        verify(buttonRepository, only()).delete(button);
    }
}