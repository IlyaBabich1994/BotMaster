package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Builder
@EqualsAndHashCode
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Button {
    @Id
    private Long id;
    private Long botId;
    private String label;
    private String callbackData;
}
