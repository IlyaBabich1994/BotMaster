package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bot {
    @Id
    private Long id;
    private String name;
    private String token;
    private String welcomeMessage;
    private Date createdAt;

}
