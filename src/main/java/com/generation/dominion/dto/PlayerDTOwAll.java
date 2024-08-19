package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.enums.E_Status;
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
    private String icon;
    
    // PLAYER
    private int gold;
    private String online;
    private String shield;

    //COMBAT INFO
    private int playerMinDmg = 1;
    private int playerMaxDmg = 1;
    private int playerHealth = 1;
    private int lastDmg;

    //RISORSE 
    private List<TroopDTO> activeTroops = new ArrayList<>();
    private List<TroopDTO> storageTroops = new ArrayList<>();
    private List<Gear> activeGears = new ArrayList<>();
    private List<Gear> storageGears = new ArrayList<>();

    private List<PlayerDTO> friends = new ArrayList<>();

    //COSTRUTTORI

    public PlayerDTOwAll(){}

    public PlayerDTOwAll(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        this.shield = player.getShield();
        this.friends = player.getFriends().stream().map(f -> new PlayerDTO(f)).toList();
        this.online = player.getOnline();
        this.icon = player.getIcon();
        
        initDTO(player);

        activeTroops = filterByStatus(player, E_Status.ACTIVE.toString());
        storageTroops = filterByStatus(player, E_Status.STORAGE.toString());
    }

    private void initDTO(Player player)
    {
        if(player.troops.size()!= 0)
            for (Troop troop : player.troops.stream().filter(t -> t.isActive()).toList()) 
            {
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
            this.activeGears = player.getGears().stream().filter(g -> g.getStatus().equalsIgnoreCase(E_Status.ACTIVE.toString())).map(g -> g.getGear()).toList();
            this.storageGears = player.getGears().stream().filter(g -> g.getStatus().equalsIgnoreCase(E_Status.STORAGE.toString())).map(g -> g.getGear()).toList();
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

        if(this.playerHealth < 0)
            this.playerHealth = 0;
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

    public void removeGold(int amount) 
    {
        this.gold -= amount;

        if(this.gold < 0)
            this.gold = 0;
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

    private List<TroopDTO> filterByStatus(Player palyer, String status)
    {
        List<TroopDTO> res = palyer.troops.stream()
                                            .filter(t -> t.getStatus().equalsIgnoreCase(status)).map(t -> new TroopDTO(t)).toList();
        return res;
    }

}