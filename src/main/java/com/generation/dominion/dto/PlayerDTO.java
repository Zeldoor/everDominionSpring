package com.generation.dominion.dto;

import com.generation.dominion.model.Player;

import lombok.Data;

@Data
public class PlayerDTO 
{
    private int id;
    private String nick;
    private int stamina;
    private int gold;

    public PlayerDTO(){}

    public PlayerDTO(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
    }
}
