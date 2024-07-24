package com.generation.dominion.dto;

import lombok.Data;

@Data
public class TroopDTO 
{
    public String className;
    public Integer minimumDamage;
    public Integer maximumDamage;
    public Integer health; 

    public TroopDTO(String className, Integer minimumDamage, Integer maximumDamage, Integer health) 
    {
        this.className = className;
        this.minimumDamage = minimumDamage;
        this.maximumDamage = maximumDamage;
        this.health = health;
    }

    public TroopDTO(){}   
}

