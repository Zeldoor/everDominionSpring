package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.model.Equipment;
import com.generation.dominion.model.Item;
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
    private List<Item> inventory = new ArrayList<>();  
    private List<Equipment> equipSlots = new ArrayList<>();

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
    }


    //METODI

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

    public boolean loseLifeEnergy() 
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

        // return random.nextInt((this.playerMaxDmg - this.playerMinDmg) + 1) + this.playerMinDmg;
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
