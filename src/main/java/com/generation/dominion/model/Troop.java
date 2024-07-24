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
    private int id;

    public String className;

    @Column(name = "min_damage")
    public Integer minDamage;

    @Column(name = "max_damage")
    public Integer maxDamage;

    @Column(name = "health")
    public Integer health;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "player_id",nullable = false)
    public Player player;
    

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

}
