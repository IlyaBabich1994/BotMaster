package com.example.demo.model;

import com.example.demo.model.enums.UserState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    private Long id;
    private Long botId;
    private Long telegramId;
    private Boolean isBlocked;
    private UserState state;

}
