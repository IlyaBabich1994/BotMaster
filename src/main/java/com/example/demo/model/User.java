package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="users")
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
