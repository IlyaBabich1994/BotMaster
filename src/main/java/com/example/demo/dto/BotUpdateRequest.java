package com.example.demo.dto;

import com.example.demo.model.BotCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BotUpdateRequest {
    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 20, message = "Name must be less than 20 characters")
    private String name;

    @NotNull(message = "Category cannot be null")
    private BotCategory category;

    private String welcomeMessage;

    @Valid
    private List<FilterRequest> filters;
}