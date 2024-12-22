package com.example.demo.services;

import com.example.demo.model.Filter;
import com.example.demo.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilterServiceImpl implements FilterService{
    private final FilterRepository filterRepository;
    @Autowired
    public FilterServiceImpl(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }
    @Override
    public List<Filter> findAllByBotId(Long botId){
        return filterRepository.findAllByBotId(botId);
    }

    @Override
    public void addFilter(Filter filter) {
        filterRepository.save(filter);
    }

    @Override
    public void removeFilter(Filter filter) {
        filterRepository.delete(filter);
    }
}
