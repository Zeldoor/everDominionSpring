package com.generation.dominion.dto;

import java.util.List;

import lombok.Data;

@Data
public class TeamDTO 
{
    private List<TroopDTO> troops;

    public List<TroopDTO> getTroops() 
    {
        return troops;
    }

    public void setTroops(List<TroopDTO> troops) 
    {
        this.troops = troops;
    }
}