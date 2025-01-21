package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class BotRequest {
    @NotEmpty
    private String name;
    private String token;
    private String category;
    private String welcomeMessage;
    private List<FilterRequest> filters;
}