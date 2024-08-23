package com.generation.dominion.model;


import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.dto.PlayerDTOwAll;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class PvePlayer 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nick;
    private int gold;
    private String icon;
    private String description;

    @Transient
    private Integer lastDmg;
    @Transient
    private Integer lastHealth;

    @OneToMany(mappedBy = "pvePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PveTroop> pveTroops = new ArrayList<>();

    public PvePlayer()
    {
        this.lastHealth = totalHealth();
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

        if(pveTroops.size()!= 0)
            for (PveTroop troop : pveTroops) 
            {
                res += troop.health;
                System.out.println(res);
            }

        return res;
    }

    public void attack(PlayerDTOwAll enemy) 
    {
        enemy.takeDamage(randomAttackInRange());
    }

    public void takeDamage(int damage) 
    {
        this.lastDmg = damage;

        if(this.lastHealth == null)
            this.lastHealth = totalHealth();

        this.lastHealth -= damage;

        if(this.lastHealth < 0)
        this.lastHealth= 0;
    }

    public int randomAttackInRange() 
    {
        if (minDamage() > maxDamage()) 
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");

        Integer diff = minDamage() - maxDamage();

        return (int)(((Math.random() * diff)+1)+minDamage());
    }

    @JsonIgnore
    public boolean isAlive() 
    {
        return this.lastHealth > 0;
    }

    @JsonIgnore
    public boolean isDead() 
    {
        return this.lastHealth <= 0;
    }
    
}