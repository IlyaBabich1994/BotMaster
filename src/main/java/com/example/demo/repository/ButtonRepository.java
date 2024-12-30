package com.example.demo.repository;

import com.example.demo.model.Button;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ButtonRepository extends JpaRepository<Button, Long> {
    List<Button> findAllByBotId(Long botId);
}
