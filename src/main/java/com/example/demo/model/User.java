package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private int id;
    private int botId;
    private int telegramId;
    private boolean status;
    private boolean state;

    //botId
    public void setBotId(int botId) {
        this.botId = botId;
    }

    public int getBotId() {
        return botId;
    }

    //telegramId
    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public int getTelegramId() {
        return telegramId;
    }
    //status
    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }
    //state
    public void setState(boolean state){
        this.state=state;
    }
    public boolean getState(){
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return status == user.status && state == user.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, state);
    }

    @Override
    public String toString() {
        return "User{" +
                "status=" + status +
                ", state=" + state +
                '}';
    }
}
