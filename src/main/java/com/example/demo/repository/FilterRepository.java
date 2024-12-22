package com.example.demo.repository;

import com.example.demo.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    List<Filter> findAllByBotId(Long botId);

}
