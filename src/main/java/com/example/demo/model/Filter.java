package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {
    @Id
    private Long id;

    @Column(name = "bot_id", insertable = false, updatable = false)
    private Long botId;

    private String pattern;

    @ElementCollection
    private List<String> action;

    @ManyToOne
    @JoinColumn(name = "bot_id", nullable = false)
    private Bot bot;
}
