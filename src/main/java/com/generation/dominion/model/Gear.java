package com.generation.dominion.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Gear;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Gear 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String icon = "";
    private Integer price;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "gear", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player_Gear> players = new ArrayList<>();

    public Gear() {}

    public Gear(Integer id, Integer price, String name, String description) 
    {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Gear(Integer price, String name, String description) 
    {
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public void getEffect(PlayerDTOwAll player)
    {
        switch (E_Gear.valueOf(name)) 
        {
            case ANELLO:

                player.setPlayerMinDmg(player.getPlayerMinDmg() + 5);
                player.setPlayerMaxDmg(player.getPlayerMaxDmg() + 5);
                
                break;

            case BRACCIALE:

                player.setPlayerHealth(player.getPlayerHealth() + 15);
                
                break;

            case COLLANA:
                
                break;

            case TIARA:
                
                break;
        
            default:
                break;
        }
    }
}