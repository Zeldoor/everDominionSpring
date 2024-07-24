package com.generation.dominion.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
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
@DiscriminatorColumn(name = "role_type")
@Table(name = "troop")
public abstract class Troop
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "damage")
    protected Integer damage;

    @Column(name = "health")
    protected Integer health;

    
    public Troop(){}



    public Troop(Integer damage, Integer health) 
    {
        this.damage = damage;
        this.health = health;
    }



    public boolean attack(Troop enemy) 
    {
        return enemy.takeDamage(this.damage);
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
}
