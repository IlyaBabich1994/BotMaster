package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString(exclude = "id")
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
