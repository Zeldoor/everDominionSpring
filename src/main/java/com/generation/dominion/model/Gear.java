package com.generation.dominion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Gear 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer price;

    private String name;

    private String description;

    private String status;

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

}