package com.example.demo.dto;

import java.util.List;

public record FilterResponse(
        Long id,
        String pattern,
        List<String> action
) {}