package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
