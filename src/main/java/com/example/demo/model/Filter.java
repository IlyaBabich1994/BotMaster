package com.example.demo.model;

import com.example.demo.model.enums.BotAction;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Filter {
    @Id
    private Long id;
    private Long botId;
    private String pattern;
    private BotAction action;
}
