package com.generation.dominion.model.subclass;

import com.generation.dominion.model.Troop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("fighter")
public class Fighter extends Troop 
{
    public Fighter() {
        super(10, 5); // Valori di default
    }

    public Fighter(Integer damage, Integer health) 
    {
        super(damage, health);
    }

    @Override
    public void specialAction(Troop ally) 
    {
        // Nessuna azione speciale per Fighter
    }
}
