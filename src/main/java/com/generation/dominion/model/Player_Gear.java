package com.generation.dominion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Player_Gear 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    Player player;

    @ManyToOne
    @JoinColumn(name = "gear_id")
    @JsonIgnore
    Gear gear;

    String status;

    public void setPlayerGear(Player p, Gear g)
    {
        player = p;
        gear = g;
        
        p.getGears().add(this);
    }
}
