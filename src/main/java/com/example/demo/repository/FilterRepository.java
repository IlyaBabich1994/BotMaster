package com.example.demo.repository;

import com.example.demo.model.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    Page findAllByBotId(Long botId, Pageable pageable);
    @Modifying
    @Query("DELETE FROM Filter f WHERE f.bot.id = :botId")
    void deleteAllByBotId(@Param("botId") Long botId);
}
