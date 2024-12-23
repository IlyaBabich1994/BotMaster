package com.example.demo.services;

import com.example.demo.model.Filter;
import com.example.demo.repository.FilterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.util.Creater.createFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FilterServiceImplTest {
    private final FilterRepository filterRepository = Mockito.mock(FilterRepository.class);
    private final FilterService filterService = new FilterServiceImpl(filterRepository);

    @Test
    public void testFindAllByBotIdShouldReturnListOfFilters() {
        Long botId = 1L;
        Filter filter1 = new Filter();
        Filter filter2 = new Filter();
        List<Filter> expectedFilters = Arrays.asList(filter1, filter2);
        when(filterRepository.findAllByBotId(botId)).thenReturn(expectedFilters);
        List<Filter> actualFilters = filterService.findAllByBotId(botId);
        assertEquals(expectedFilters, actualFilters);
        verify(filterRepository, times(1)).findAllByBotId(botId);
    }

    @Test
    public void testCreateFilter() {
        Filter filter = createFilter();
        filterService.addFilter(filter);
        verify(filterRepository, only()).save(filter);
    }

    @Test
    public void testDeleteFilter() {
        Filter filter = createFilter();
        filterService.removeFilter(filter);
        verify(filterRepository, only()).delete(filter);
    }
}