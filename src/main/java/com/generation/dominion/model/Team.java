package com.generation.dominion.model;

import java.util.List;

import jakarta.persistence.Entity;

public class Team 
{
    private List<Troop> troops;


    public Team(List<Troop> troops) 
    {
        this.troops = troops;
    }

    public List<Troop> getTroops() 
    {
        return troops;
    }

    public boolean isAlive() 
    {
        return troops.stream().anyMatch(Troop::isAlive);
    }
}