package com.example.demo.Services;

import com.example.demo.model.Button;

public interface ButtonService {
    String addButton(Button button);// отдаём тест + булеан с состоянием
    String removeButton(Button button); // тоже самое
}
