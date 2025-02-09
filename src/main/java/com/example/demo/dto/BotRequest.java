package com.example.demo.dto;

import com.example.demo.model.BotCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class BotRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Token cannot be empty")
    private String token;
    @NotNull
    private BotCategory category;
    private String welcomeMessage;
    private List<FilterRequest> filters;
}