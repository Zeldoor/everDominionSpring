package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.Entity;

@Entity
public class Fighter extends Troop 
{
    public Fighter() {
        super(10, 5); // Valori di default
    }

    public Fighter(Integer damage, Integer defence) 
    {
        super(damage, defence);
    }

    @Override
    public void specialAction(Troop ally) 
    {
        // Nessuna azione speciale per Fighter
    }
}
