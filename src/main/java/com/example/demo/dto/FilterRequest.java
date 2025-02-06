package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    @NotBlank(message = "Тип действия обязателен")
    @Pattern(regexp = "ADD|UPDATE|DELETE", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String action;

    @NotBlank(message = "Тип фильтра обязателен")
    private String type;

    @NotBlank(message = "Паттерн фильтра не может быть пустым")
    private String pattern;
}