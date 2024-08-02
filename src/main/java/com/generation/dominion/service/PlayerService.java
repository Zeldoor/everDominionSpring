package com.generation.dominion.service;

import org.springframework.stereotype.Service;

import com.generation.dominion.model.Troop;

@Service
public class PlayerService 
{
    public Troop switchSingleState(Troop troop)
    {
        if(troop.isActive())
            troop.setStorage();
        else
            troop.setActive();

        return troop;
    }
}