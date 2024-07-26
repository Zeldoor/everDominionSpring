package com.generation.dominion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipment")
@Data
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
}
