package com.generation.dominion.dto;

import lombok.Data;

@Data
public class TroopDTO 
{
    public String  role;
    public Integer minimumDamage;
    public Integer maximumDamage;
    public Integer health; 

    public TroopDTO(String role, Integer minimumDamage, Integer maximumDamage, Integer health) {
        this.role = role;
        this.minimumDamage = minimumDamage;
        this.maximumDamage = maximumDamage;
        this.health = health;
    }

    public TroopDTO()
    
    {

    }   
}

