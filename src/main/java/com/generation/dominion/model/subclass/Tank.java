package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.Entity;

@Entity
public class Tank extends Troop 
{
    public Tank() 
    {
        super(5, 10); // Esempio: più defense che damage
    }

    public Tank(Integer damage, Integer defense) 
    {
        super(damage, defense);
    }

    @Override
    public void specialAction(Troop ally) 
    {
        // Nessuna azione speciale per Tank
    }
}
