package com.generation.dominion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Equipment extends Item 
{
    @Column(nullable = false)
    private String type;

    public Equipment(String name, String description, int price, String type) 
    {
        super(name, description, price);
        this.type = type;
    }


    public String getType() 
    {
        return type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }
    
}
