package com.generation.dominion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "player")
public class Player 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nick;
    private int lifeEnergy = 3; //life energy base è 3
    private int gold = 100; // gold di partenza è 100

    @OneToOne
    private Team team;

    public int getLifeEnergy() 
    {
        return lifeEnergy;
    }

    public void setLifeEnergy(int lifeEnergy) 
    {
        this.lifeEnergy = lifeEnergy;
    }

    public boolean loseLifeEnergy() 
    {
        if (this.lifeEnergy > 0) 
        {
            this.lifeEnergy--;
        }

        return isDead();
    }

    public int getGold() 
    {
        return gold;
    }

    public void setGold(int gold) 
    {
        this.gold = gold;
    }

    public void addGold(int amount) 
    {
        this.gold += amount;
    }

    public boolean isDead() 
    {
        return this.lifeEnergy <= 0;
    }
}
