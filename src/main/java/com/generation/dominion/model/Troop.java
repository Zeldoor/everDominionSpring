package com.generation.dominion.model;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.dto.TroopDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Troop
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String className;

    @Column(name = "min_damage")
    protected Integer minDamage;

    @Column(name = "max_damage")
    protected Integer maxDamage;

    @Column(name = "health")
    protected Integer health;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "player_id",nullable = false)
    Player player;
    

    public Troop(){}

    public Troop(Integer damage, Integer health) 
    {
        this.minDamage = damage - 2;
        this.maxDamage = damage + 2;
        this.health = health;
    }

    public Troop(TroopDTO dto) 
    {
        this.className = dto.getClassName();
        this.minDamage = dto.getMinDamage();
        this.maxDamage = dto.getMaxDamage();
        this.health = dto.getHealth();
    }

    public Troop(TroopDTO dto, Player player) 
    {
        this.className = dto.getClassName();
        this.minDamage = dto.getMinDamage();
        this.maxDamage = dto.getMaxDamage();
        this.health = dto.getHealth();
        this.player = player;
    }

    public boolean attack(Troop enemy) 
    {
        return enemy.takeDamage(randomAttackInRange());
    }

    public boolean takeDamage(Integer damage) 
    {
        this.health -= damage;
        return isDead();
    }

    public boolean isAlive() 
    {
        return this.health > 0;
    }

    public boolean isDead() 
    {
        return this.health <= 0;
    }

    public int randomAttackInRange() 
    {
        Random random = new Random();

        if (this.minDamage > this.maxDamage) 
        {
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");
        }

        return random.nextInt((this.maxDamage - this.minDamage) + 1) + this.minDamage;
    }
}
