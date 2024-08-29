package com.generation.dominion.model;

import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class TroopInShop 
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "damage")
    public Integer damage;
    
    public String className;
    public Integer health;
    public Integer price;
    
    @Transient  //evita che venga aggiunta al db
    @JsonIgnore
    Random random = new Random();

    public Integer minDamage()
    {
        return this.damage - random.nextInt((className.equalsIgnoreCase("archer") ? 3 : 2)); 
    }

    public Integer maxDamage()
    {
        return random.nextInt((className.equalsIgnoreCase("archer") ? 10 : 6)) + this.damage; 
    }

    public Integer randomHealth()
    {
        return random.nextInt((className.equalsIgnoreCase("archer") ? 5 : 10)) + this.health; 
    }
}   
