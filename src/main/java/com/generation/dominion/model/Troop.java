package com.generation.dominion.model;

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

    protected Integer damage;
    protected Integer defence;

    
    public Troop(){}



    public Troop(Integer damage, Integer defence) 
    {
        this.damage = damage;
        this.defence = defence;
    }



    public boolean attack(Troop enemy) 
    {
        return enemy.takeDamage(this.damage);
    }



    public boolean takeDamage(Integer damage) 
    {
        this.defence -= damage;
        return isDead();
    }



    public boolean isAlive() 
    {
        return this.defence > 0;
    }



    public boolean isDead() 
    {
        return this.defence <= 0;
    }

    // Metodi per i ruoli specifici
    public abstract void specialAction(Troop ally);
}
