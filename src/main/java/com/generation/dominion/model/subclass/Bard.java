package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("bard")
public class Bard extends Troop 
{
    public Bard() 
    {
        super(0, 5); // Esempio: nessun damage, difesa media
    }

    public Bard(Integer damage, Integer health)
    {
        super(damage, health);
    }

    @Override
    public void specialAction(Troop ally) 
    {
        if (ally != null && ally.isAlive())
        {
            // ally.setDamage(ally.getDamage() + 5); // Potenzia il damage degli alleati
        }
    }
}
