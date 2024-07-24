package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.Entity;

@Entity
public class Bard extends Troop 
{
    public Bard() 
    {
        super(0, 5); // Esempio: nessun damage, difesa media
    }

    @Override
    public void specialAction(Troop ally) 
    {
        ally.setMinDamage(ally.getMinDamage() + 5); // Potenzia il damage degli alleati
        ally.setMaxDamage(ally.getMaxDamage() + 5); // Potenzia il damage degli alleati
    }
}
