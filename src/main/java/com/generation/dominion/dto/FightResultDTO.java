package com.generation.dominion.dto;

import java.util.List;

import lombok.Data;

@Data
public class FightResultDTO 
{
    private PlayerDTOwAll attacker;
    private PlayerDTOwAll defender;
    private List<String> results;
    private List<Integer> playerHealth;
    private List<Integer> enemyHealth;
    private boolean victory;

    public void addPlayerHeath(Integer playerHealth)
    {
        this.playerHealth.add(playerHealth);
    }

    public void addEnemyHealth(Integer enemyHealth)
    {
        this.enemyHealth.add(enemyHealth);
    }
}
