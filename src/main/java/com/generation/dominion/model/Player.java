package com.generation.dominion.model;


import java.util.List;

import com.generation.dominion.dto.PlayerDTOwTroops;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    public String nick;
    public int lifeEnergy;
    public int gold;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Troop> troops;

    public Player()
    {
        this.gold = 100;
        this.lifeEnergy = 3;
    }

    public Player(PlayerDTOwTroops playerDto)
    {
        this.nick = playerDto.getNick();
        this.gold = playerDto.getGold();
        this.lifeEnergy = playerDto.getLifeEnergy();
    }

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
