package com.example.demo.repository;

import com.example.demo.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class FilterRepository {
    private List<Filter> filters = new ArrayList<>();

    public List<Filter> findAllByBotId(Long botId) {
        List<Filter> result = new ArrayList<>();
        for (Filter filter : filters) {
            if (filter.getBotId().equals(botId)) {
                result.add(filter);
            }
        }
        return result;
    }
}
