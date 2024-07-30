package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;

@Data
public class PlayerDTOwTroops 
{
    //INFO
    private int id;
    private String nick;
    private int stamina;
    private int gold;
    
    //COMBAT INFO
    private int playerMinDmg;
    private int playerMaxDmg;
    private int playerHealth;
    private int lastDmg;

    //RISORSE 
    private List<TroopDTO> troops = new ArrayList<>();
    private List<Gear> activeGears = new ArrayList<>();

    //COSTRUTTORI

    public PlayerDTOwTroops(){}

    public PlayerDTOwTroops(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        
        initDTO(player);
    }

    private void initDTO(Player player)
    {
        if(player.troops.size()!= 0)
            for (Troop troop : player.troops) 
            {
                TroopDTO dto = new TroopDTO(troop);
                this.troops.add(dto);

                this.playerMinDmg += troop.minDamage;
                this.playerMaxDmg += troop.maxDamage;
                this.playerHealth += troop.health;
            }
        else
        {
            this.playerMinDmg = 1;
            this.playerMaxDmg = 1;
            this.playerHealth = 1;
        }
        if(player.getGears().size() != 0) 
        {
            this.activeGears.addAll(player.getGears());
        }
    }


    //METODI

    public boolean addItemToInventory(Gear gear)  // Questi sono i gear attivi durante il fight
    {
        return this.activeGears.add(gear);
    }

    public void buyGear(Gear gear)  // compra un gear
    {
        this.gold -= gear.getPrice();
    }
   
    public boolean addActiveTroop(Troop troop) // Queste sono le troop attive durante il fight
    {
        return this.troops.add(new TroopDTO(troop));
    }
    
    public void attack(PlayerDTOwTroops enemy) 
    {
        enemy.takeDamage(randomAttackInRange());
    }

    public void takeDamage(int damage) 
    {
        this.lastDmg = damage;
        this.playerHealth -= damage;
    }

    public boolean isAlive() 
    {
        return this.playerHealth > 0;
    }

    public boolean isDead() 
    {
        return this.playerHealth <= 0;
    }

    public void addGold(int amount) 
    {
        this.gold += amount;
    }

    public boolean loseStamina() 
    {
        if (this.stamina > 0) 
        {
            this.stamina--;
        }

        return isDead();
    }

    public int randomAttackInRange() 
    {
        if (this.playerMinDmg > this.playerMaxDmg) 
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");

        Integer diff = this.playerMaxDmg - this.playerMinDmg;

        return (int)(((Math.random() * diff)+1)+this.playerMinDmg);
    }

}