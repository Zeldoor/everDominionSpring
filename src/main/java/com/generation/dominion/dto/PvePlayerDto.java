package com.generation.dominion.dto;

import java.util.List;

import com.generation.dominion.model.PvePlayer;
import com.generation.dominion.model.PveTroop;

import lombok.Data;

@Data
public class PvePlayerDto 
{
    private int id;
    private String nick;
    private int gold;
    private String icon;
    private int pveMinDmg;
    private int pveMaxDmg;
    private int pveHealth;

    private String description;

    private List<PveTroop> pveTroops;

    public PvePlayerDto(PvePlayer pvePlayer) 
    {
        this.id = pvePlayer.getId();
        this.nick = pvePlayer.getNick();
        this.gold = pvePlayer.getGold();
        this.icon = pvePlayer.getIcon();
        this.description = pvePlayer.getDescription();
        this.pveTroops = pvePlayer.getPveTroops();
        
        initDTO(pvePlayer);
    }

    private void initDTO(PvePlayer pvePlayer)
    {
        if(pvePlayer.getPveTroops().size()!= 0)
            for (PveTroop troop : pvePlayer.getPveTroops()) 
            {
                this.pveMinDmg += troop.minDamage;
                this.pveMaxDmg += troop.maxDamage;
                this.pveHealth += troop.health;
            }
        else
        {
            this.pveMinDmg = 1;
            this.pveMaxDmg = 1;
            this.pveHealth = 1;
        }
    }
}
