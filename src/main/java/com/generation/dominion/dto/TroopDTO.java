package com.generation.dominion.dto;

import lombok.Data;

@Data
public class TroopDTO 
{
    public String className;
    public Integer minDamage;
    public Integer maxDamage;
    public Integer health; 
    public Integer playerId;

    public TroopDTO(String className, Integer minDamage, Integer maxDamage, Integer health) 
    {
        this.className = className;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.health = health;
    }

    public TroopDTO(){}   
}

