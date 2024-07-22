package com.generation.progetto_finale.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "troop")
public class Troop 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer damage;
    private Integer defence;

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
        return this.defence>0;
    }

    public boolean isDead()
    {
        return this.defence<=0;
    }
}