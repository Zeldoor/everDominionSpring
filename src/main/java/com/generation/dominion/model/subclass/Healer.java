package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.Entity;

@Entity
public class Healer extends Troop 
{
    public Healer() 
    {
        super(0, 5); // Esempio: nessun damage, defense bassa
    }

    @Override
    public void specialAction(Troop ally) 
    {
        ally.setHealth(ally.getHealth() + 5); // Potenzia la defense dei compagni
    }
}
