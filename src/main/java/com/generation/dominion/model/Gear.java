package com.generation.dominion.model;

import java.util.ArrayList;
import java.util.List;

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

    public void getEffect(Player player)
    {
        switch (name) 
        {
            case "anello":
                

                break;
        
            default:
                break;
        }
    }
}