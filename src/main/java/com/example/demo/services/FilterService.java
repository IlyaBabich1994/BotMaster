package com.example.demo.services;

import com.example.demo.model.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FilterService  {
    Page findAllByBotId(Long botId,Pageable pageable);

    void addFilter(Filter filter);

    void removeFilter(Filter filter);
}
