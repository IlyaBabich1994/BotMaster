package com.example.demo.dto;

import com.example.demo.model.Filter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BotResponse {
    private Long id;
    private String name;
    private List<Filter> filters;
    private String status;
    private Date createdAt;

    public BotResponse(Long id, String name, String welcomeMessage, List<FilterResponse> mapFilters) {
    }
}