package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class BotRequest {
    private String name;
    private String token;
    private String category;
    private String welcomeMessage;
    private List<FilterRequest> filters; // Список фильтров
}