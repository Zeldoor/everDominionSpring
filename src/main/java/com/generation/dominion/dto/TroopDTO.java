package com.generation.dominion.dto;

import java.util.Random;

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

    public int randomAttackInRange() 
    {
        Random random = new Random();

        if (this.minimumDamage > this.maximumDamage) 
        {
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");
        }

        return random.nextInt((this.maximumDamage - this.minimumDamage) + 1) + this.minimumDamage;
    }
}

