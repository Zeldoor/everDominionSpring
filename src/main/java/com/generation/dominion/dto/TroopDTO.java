package com.generation.dominion.dto;

import lombok.Data;

@Data
public class TroopDTO 
{
    private Integer damage;
    private Integer defence;

    
    public Integer getDamage() 
    {
        return damage;
    }



    public void setDamage(Integer damage) 
    {
        this.damage = damage;
    }



    public Integer getDefence() 
    {
        return defence;
    }



    public void setDefence(Integer defence) 
    {
        this.defence = defence;
    }
}