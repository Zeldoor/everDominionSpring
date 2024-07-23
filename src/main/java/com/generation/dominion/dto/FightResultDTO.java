package com.generation.dominion.dto;

import lombok.Data;

@Data
public class FightResultDTO 
{
    private String result;


    public String getResult() 
    {
        return result;
    }



    public void setResult(String result) 
    {
        this.result = result;
    }
}
