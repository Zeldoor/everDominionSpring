package com.generation.dominion.dto;

import com.generation.dominion.model.Troop;

import lombok.Data;

@Data
public class TroopDTO 

{
    private Integer id;
    private String className;
    private Integer minDamage;
    private Integer maxDamage;
    private Integer health; 
    private Integer playerId;
    private String status;
    private Integer price;

    public TroopDTO(){}

    public TroopDTO(Troop t)
    {
        this.id = t.getId();
        this.className = t.getClassName();
        this.minDamage = t.getMinDamage();
        this.maxDamage = t.getMaxDamage();
        this.health = t.getHealth();
        this.status = t.getStatus();
        this.price = t.getPrice();
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