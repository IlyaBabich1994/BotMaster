package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
}
