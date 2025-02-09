package com.example.demo.services;

import com.example.demo.model.Filter;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface FilterService  {
    Page findAllByBotId(Long botId,Pageable pageable);

    void addFilter(Filter filter);

    void removeFilter(Filter filter);
    void deleteAllByBotId(Long botId);
}
