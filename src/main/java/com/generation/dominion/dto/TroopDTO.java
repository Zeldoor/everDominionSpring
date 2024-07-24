package com.generation.dominion.dto;

import com.generation.dominion.model.Troop;

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

    public TroopDTO(Troop t)
    {
        this.className = t.getClassName();
        this.minDamage = t.getMinDamage();
        this.maxDamage = t.getMaxDamage();
        this.health = t.getHealth();
        this.playerId = t.getPlayer().getId();
    }
}

