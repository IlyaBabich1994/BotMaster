package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private Long id;
    private Long botId;
    private Long telegramId;
    private boolean status;
    private boolean state;
}
