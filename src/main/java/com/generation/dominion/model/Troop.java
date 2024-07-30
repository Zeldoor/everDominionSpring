package com.generation.dominion.model;


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
    private int id;                                         //NON TOCCARE

    public String className;

    @Column(name = "min_damage")
    public Integer minDamage;

    @Column(name = "max_damage")
    public Integer maxDamage;

    @Column(name = "health")
    public Integer health;

    public String status;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = true)
    @JsonIgnore
    Player player;

    @Column(name = "price")
    public Integer price;    

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

    public Troop(TroopInShop troopShop) 
    {
        this.className = troopShop.getClassName();
        this.minDamage = troopShop.minDamage();
        this.maxDamage = troopShop.maxDamage();
        this.health = troopShop.randomHealth();
        this.price = troopShop.getPrice();
    }

    public Troop(TroopDTO dto, Player player) 
    {
        this.className = dto.getClassName();
        this.minDamage = dto.getMinDamage();
        this.maxDamage = dto.getMaxDamage();
        this.health = dto.getHealth();
        this.player = player;
    }

    //costruttore per shop 
    public Troop(String className, Integer minDamage, Integer maxDamage, Integer health, Integer price) 
    {
        this.className = className;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.health = health;
        this.price = price;
    }

    public boolean isActive()
    {
        return this.status.equals("active");
    }
}