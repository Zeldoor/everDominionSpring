package com.generation.dominion.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.model.PvePlayer;
import com.generation.dominion.model.PveTroop;

import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class PvePlayerDto 
{
    private int id;
    private String nick;
    private int gold;
    private String icon;
    private int pveMinDmg;
    private int pveMaxDmg;
    private int pveHealth;

    private String description;

    private List<PveTroop> pveTroops;

    @Transient
    private Integer lastDmg = 0;

    public PvePlayerDto(PvePlayer pvePlayer) 
    {
        this.id = pvePlayer.getId();
        this.nick = pvePlayer.getNick();
        this.gold = pvePlayer.getGold();
        this.icon = pvePlayer.getIcon();
        this.description = pvePlayer.getDescription();
        this.pveTroops = pvePlayer.getPveTroops();
        
        initDTO(pvePlayer);
    }

    private void initDTO(PvePlayer pvePlayer)
    {
        if(pvePlayer.getPveTroops().size()!= 0)
            for (PveTroop troop : pvePlayer.getPveTroops()) 
            {
                this.pveMinDmg += troop.minDamage;
                this.pveMaxDmg += troop.maxDamage;
                this.pveHealth += troop.health;
            }
        else
        {
            this.pveMinDmg = 1;
            this.pveMaxDmg = 1;
            this.pveHealth = 1;
        }
    }

    public Integer maxDamage()
    {
        Integer res = 1;

        if(pveTroops.size() != 0)
            for (PveTroop troop : pveTroops) 
            {
                res += troop.maxDamage;
            }

        return res;
    }

    public Integer minDamage()
    {
        Integer res = 0;

        if(pveTroops.size()!= 0)
        for (PveTroop troop : pveTroops) 
        {
            res += troop.minDamage;
        }

        return res;
    }

    public Integer totalHealth()
    {
        Integer res = 0;

        if(pveTroops.size() != 0 )
            for (PveTroop troop : pveTroops) 
            {
                res += troop.health;
            }

        return res;
    }

    public void attack(PlayerDTOwAll enemy) 
    {
        enemy.takeDamage(randomAttackInRange());
    }

    public void takeDamage(Integer damage) 
    {
        this.lastDmg = damage;
        this.pveHealth -= damage;

        if(this.pveHealth < 0)
        this.pveHealth= 0;
    }

    public Integer randomAttackInRange() 
    {
        if (minDamage() > maxDamage()) 
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");

        Integer diff = minDamage() - maxDamage();

        return (int)(((Math.random() * diff)+1)+minDamage());
    }

    @JsonIgnore
    public boolean isAlive() 
    {
        return this.pveHealth > 0;
    }

    @JsonIgnore
    public boolean isDead() 
    {
        return this.pveHealth <= 0;
    }
}
