package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;


@Entity
@ToString
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {
    @Id
    private Long id;
    private Long botId;
    private String patern;
    private List<String> action;
}
