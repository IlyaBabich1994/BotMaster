package com.example.demo.repository;

import com.example.demo.model.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    Page findAllByBotId(Long botId, Pageable pageable);
}
