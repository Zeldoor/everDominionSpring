package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;

@Data
public class PlayerDTOwTroops 
{
    private Integer id;
    private String nick;
    private int playerMinDmg;
    private int playerMaxDmg;
    private int playerHealth; 
    
    private int lifeEnergy;
    private int gold;
    
    private List<TroopDTO> troops = new ArrayList<>();


    public PlayerDTOwTroops(Player player) 
    {
        this.nick = player.nick;
        this.lifeEnergy = player.lifeEnergy;
        this.gold = player.gold;
        
        initDTO(player);
    }

    public PlayerDTOwTroops(){}

    private void initDTO(Player player)
    {
        for (Troop troop : player.troops) 
        {
            TroopDTO dto = new TroopDTO(troop);
            this.troops.add(dto);

            this.playerMinDmg += troop.minDamage;
            this.playerMaxDmg += troop.maxDamage;
            this.playerHealth += troop.health;
        }
    }
}
