package com.example.demo.services;

import com.example.demo.model.Filter;
import com.example.demo.repository.FilterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.demo.util.Creater.createFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FilterServiceImplTest {
    private final FilterRepository filterRepository = Mockito.mock(FilterRepository.class);
    private final FilterService filterService = new FilterServiceImpl(filterRepository);

    @Test
    public void testFindAllByBotIdShouldReturnListOfFilters() {
        Long botId = 1L;
        Pageable pageable;
        Page<Filter> filterPage = new PageImpl<>(Collections.emptyList());
        pageable = Pageable.unpaged();
        when(filterRepository.findAllByBotId(botId,pageable)).thenReturn(filterPage);
        Page result = filterService.findAllByBotId(botId, pageable);
        verify(filterRepository, times(1)).findAllByBotId(botId, pageable);

        assertEquals(filterPage, result);
    }

    @Test
    public void testAddFilterNullFilter() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            filterService.addFilter(null);
        });
        assertEquals("Filter must not be null", exception.getMessage());
        verify(filterRepository, never()).save(any(Filter.class));
    }

    @Test
    public void testAddFilterCorectFilter() {
        Filter filter = createFilter();
        filterService.addFilter(filter);
        verify(filterRepository, times(1)).save(filter);
    }

    @Test
    public void testRemoveFilterNullFilter() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            filterService.removeFilter(null);
        });
        assertEquals("Filter must not be null", exception.getMessage());
        verify(filterRepository, never()).delete(any(Filter.class));
    }

    @Test
    public void testRemoveFilterFilterNotFound() {
        Filter filter = createFilter();
        when(filterRepository.existsById(filter.getId())).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            filterService.removeFilter(filter);
        });
        assertEquals("Filter not found", exception.getMessage());
        verify(filterRepository, never()).delete(filter);
    }

    @Test
    public void testRemoveFilterCorrect() {
        Filter filter = createFilter();
        when(filterRepository.existsById(filter.getId())).thenReturn(true);
        filterService.removeFilter(filter);
        verify(filterRepository, times(1)).delete(filter);
    }
}