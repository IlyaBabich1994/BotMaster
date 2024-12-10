package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Bot {
    @Id
    private int id;
    private String name;
    private String token;
    private String welcomeMessage;
    private Date createdAt;
//name
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
//welcomeMessage
    public void setWelcomeMessage(String welcomeMessage){
        this.welcomeMessage = welcomeMessage;
    }
    public String getWelcomeMessage(){
        return welcomeMessage;
    }
//token
    public void setToken (String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
//date
    private String setDate(Date currentDate){
        this.createdAt = currentDate;
        return "Creation date is " + currentDate;
    }
    public String getDate(){
        return "Creation date is " + createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bot bot = (Bot) o;
        return name.equals(bot.name) && token.equals(bot.token) && Objects.equals(welcomeMessage, bot.welcomeMessage) && createdAt.equals(bot.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, token, welcomeMessage, createdAt);
    }

    @Override
    public String toString() {
        return "Bot{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
