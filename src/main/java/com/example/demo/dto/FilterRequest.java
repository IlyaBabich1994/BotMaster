package com.example.demo.dto;

import lombok.Data;

@Data
public class FilterRequest {
    private String pattern;
    private String action;

    public FilterRequest(String s, String allow) {

    }
}