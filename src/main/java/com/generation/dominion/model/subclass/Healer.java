package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("healer")
public class Healer extends Troop 
{
    public Healer() 
    {
        super(0, 5); // Esempio: nessun damage, defense bassa
    }

    public Healer(Integer damage, Integer health)
    {
        super(damage, health);
    }

    @Override
    public void specialAction(Troop ally) 
    {
        if (ally != null && ally.isAlive())
        {
            ally.setHealth(ally.getHealth() + 5); // Potenzia la defense dei compagni
        }
    }
}
