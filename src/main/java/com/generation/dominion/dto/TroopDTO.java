package com.generation.dominion.dto;

import com.generation.dominion.model.Troop;

import lombok.Data;

@Data
public class TroopDTO 

{
    public Integer id;
    public String className;
    public Integer minDamage;
    public Integer maxDamage;
    public Integer health; 
    public Integer playerId;
    public String status;

    public TroopDTO(String className, Integer minDamage, Integer maxDamage, Integer health, String status) 
    {
        this.className = className;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.health = health;
        this.status = status;
    }

    public TroopDTO(){}   

    public TroopDTO(Troop t)
    {
        this.id = t.getId();
        this.className = t.getClassName();
        this.minDamage = t.getMinDamage();
        this.maxDamage = t.getMaxDamage();
        this.health = t.getHealth();
        this.status = t.getStatus();
    }

    public void setActive()
    {
        status = "active";
    }

    public void setStorage()
    {
        status = "storage";
    }
}