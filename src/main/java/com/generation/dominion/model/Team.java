package com.generation.dominion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "team")
public class Team 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToMany
    private List<Troop> troops;



    public Team() {}



    public Team(List<Troop> troops) 
    {
        this.troops = troops;
    }

    
    public boolean isAlive() 
    {
        return troops.stream().anyMatch(Troop::isAlive);
    }


    public Troop getAliveTroop() 
    {
        return troops.stream().filter(Troop::isAlive).findFirst().orElse(null);
    }


    public void performSpecialActions(Troop actingTroop) 
    {
        troops.forEach(troop -> troop.specialAction(actingTroop));
    }
}
