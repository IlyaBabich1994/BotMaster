package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {
    @Id
    private int id;
    private String botId;
    private String patern;
    private List<String> action;
//botId
    public void setBotId(String botId){
        this.botId = botId;
    }
    public String getBotId(){
        return botId;
    }
//patern
    public void setPatern(String patern){
        this.patern=patern;
    }
     public String getPatern(){
        return patern;
     }
//action
    public void setAction(List<String> actionList){
        this.action=actionList;
    }
    public List<String> getAction(){
        //action.stream().forEach(a-> System.out.println(a));
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return Objects.equals(patern, filter.patern) && Objects.equals(action, filter.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patern, action);
    }

    @Override
    public String toString() {
        return "Filter{" +
                "patern='" + patern + '\'' +
                ", action=" + action +
                '}';
    }
}
