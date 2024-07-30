package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;

@Data
public class PlayerDTOwAll 
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
<<<<<<< HEAD:src/main/java/com/generation/dominion/dto/PlayerDTOwTroops.java
    private List<TroopDTO> troops = new ArrayList<>(); //Troops attive
    private List<Gear> gears = new ArrayList<>(); //Gears attivi

    /*
    private List<TroopDTO> StorageTroops = new ArrayList<>(); // Troops conservate
    private List<Gear> StorageGears = new ArrayList<>(); // Gears conservati
     */

=======
    private List<TroopDTO> activeTroops = new ArrayList<>();
    private List<Gear> activeGears = new ArrayList<>();
    private List<TroopDTO> storageTroops = new ArrayList<>();
    private List<Gear> storageGears = new ArrayList<>();
>>>>>>> ec535bc97edc71f47b9e058259bfd2f9525ce495:src/main/java/com/generation/dominion/dto/PlayerDTOwAll.java

    //COSTRUTTORI

    public PlayerDTOwAll(){}

    public PlayerDTOwAll(Player player) 
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
                this.activeTroops.add(dto);

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
<<<<<<< HEAD:src/main/java/com/generation/dominion/dto/PlayerDTOwTroops.java
            this.gears.addAll(player.getGears());
            this.gears.addAll(player.getGears());
=======
            this.activeGears.addAll(player.getGears());
>>>>>>> ec535bc97edc71f47b9e058259bfd2f9525ce495:src/main/java/com/generation/dominion/dto/PlayerDTOwAll.java
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
        return this.activeTroops.add(new TroopDTO(troop));
    }
    
    public void attack(PlayerDTOwAll enemy) 
    {
        enemy.takeDamage(randomAttackInRange());
    }

    public void takeDamage(int damage) 
    {
        this.lastDmg = damage;
        this.playerHealth -= damage;
    }

    @JsonIgnore
    public boolean isAlive() 
    {
        return this.playerHealth > 0;
    }

    @JsonIgnore
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