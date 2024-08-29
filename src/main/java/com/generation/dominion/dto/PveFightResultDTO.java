package com.generation.dominion.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PveFightResultDTO 
{
    private PlayerDTOwAll player;
    private List<String> results = new ArrayList<>();
    private List<Integer> playerHealth = new ArrayList<>();
    private List<Integer> enemyHealth = new ArrayList<>();
    private Integer gold;
    private Boolean victory;

    public void addPlayerHeath(Integer playerHealth)
    {
        this.playerHealth.add(playerHealth);
    }

    public void addEnemyHealth(Integer enemyHealth)
    {
        this.enemyHealth.add(enemyHealth);
    }
}