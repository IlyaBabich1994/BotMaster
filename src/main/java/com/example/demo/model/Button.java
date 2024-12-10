package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Button {
    @Id
    private int id;
    private int botId;
    private String label;
    private String callbackData;

    //botId
    public void setBotId(int botId){
        this.botId=botId;
    }
    public int getBotId(){
        return botId;
    }
    //label
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
    //callbackData
    public void setCallbackData(String callbackData){
        this.callbackData=callbackData;
    }
    public String getCallbackData(){
        return callbackData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return botId == button.botId && label.equals(button.label) && callbackData.equals(button.callbackData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(botId, label, callbackData);
    }

    @Override
    public String toString() {
        return "Button{" +
                "label='" + label + '\'' +
                ", callbackData='" + callbackData + '\'' +
                '}';
    }
}
