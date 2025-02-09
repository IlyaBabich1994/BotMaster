package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BotUpdateRequest {
    @Size(min = 3, max = 20)
    private String name;

    @Size(max = 500)
    private String welcomeMessage;

    @Valid
    @NotNull
    private List<FilterRequest> filters;

    public BotUpdateRequest(String existing, Object o) {
    }
}