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
    private String icon;

    public PlayerDTO(){}

    public PlayerDTO(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        this.icon = player.getIcon();
    }

    public PlayerDTO(PlayerDTOwAll player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        this.icon = player.getIcon();
    }
}
