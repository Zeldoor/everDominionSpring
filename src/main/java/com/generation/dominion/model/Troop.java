package com.generation.dominion.model;

import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "troop")
public abstract class Troop 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    protected Integer minDamage;
    protected Integer maxDamage;
    protected Integer health;

    
    public Troop(){}

    public Troop(Integer damage, Integer defence) 
    {
        this.minDamage = damage - 2;
        this.maxDamage = damage + 2;
        this.health = defence;
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

    // Metodi per i ruoli specifici
    public abstract void specialAction(Troop ally);

    
   

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
