package com.example.demo.services;

import com.example.demo.model.Filter;

import java.util.List;

public interface FilterService {
    List<Filter> findAllByBotId(Long botId);

    void addFilter(Filter filter);

    void removeFilter(Filter filter);
}
