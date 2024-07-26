package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.generation.dominion.model.Equipment;
import com.generation.dominion.model.Item;
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
    private List<Item> inventory = new ArrayList<>(10);
    private List<Equipment> equipSlots = new ArrayList<>(3);


    public PlayerDTOwTroops(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.lifeEnergy = player.getLifeEnergy();
        this.gold = player.getGold();
        
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

    public String attack(PlayerDTOwTroops enemy) 
    {
        return enemy.takeDamage(randomAttackInRange());
    }

    public String takeDamage(Integer damage) 
    {
        Integer res = this.playerHealth - damage;
        this.playerHealth -= damage;

        return res+"";
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

    public boolean loseLifeEnergy() 
    {
        if (this.lifeEnergy > 0) 
        {
            this.lifeEnergy--;
        }

        return isDead();
    }

    public int randomAttackInRange() 
    {
        Random random = new Random();

        if (this.playerMinDmg > this.playerMaxDmg) 
            throw new IllegalArgumentException("Il valore minimo deve essere minore o uguale al valore massimo.");

        return random.nextInt((this.playerMaxDmg - this.playerMinDmg) + 1) + this.playerMinDmg;
    }

    public boolean addItemToInventory(Item item) 
    {
        if (inventory.size() < 10) 
        {
            inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean equipItem(Equipment equipment) 
    {
        if (equipSlots.size() < 3) 
        {
            equipSlots.add(equipment);
            return true;
        }
        return false;
    }

    public void buyItem(Item item) 
    {
        if (gold >= item.getPrice()) 
        {
            gold -= item.getPrice();
            addItemToInventory(item);
        }
    }
}
