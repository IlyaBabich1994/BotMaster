package com.example.demo.services;

import com.example.demo.model.Filter;
import com.example.demo.repository.FilterRepository;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class FilterServiceImpl implements FilterService {
    private static final Logger logger = LoggerFactory.getLogger(FilterServiceImpl.class);

    private final FilterRepository filterRepository;

    @Autowired
    public FilterServiceImpl(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @Override
    public Page findAllByBotId(Long botId, Pageable pageable) {
        return filterRepository.findAllByBotId(botId,pageable);
    }

    @Override
    public void addFilter(Filter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter must not be null");
        } else {
            filterRepository.save(filter);
        }
    }

    @Override
    public void removeFilter(Filter filter) {
        if (filter == null) {
            logger.error("Filter is null");
            throw new IllegalArgumentException("Filter must not be null");
        }
        if (!filterRepository.existsById(filter.getId())) {
            logger.error("Filter with ID {} not found", filter.getId());
            throw new IllegalArgumentException("Filter not found");
        }
        filterRepository.delete(filter);
    }
}
