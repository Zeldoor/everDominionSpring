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
    private List<TroopDTO> activeTroops = new ArrayList<>();
    private List<TroopDTO> storageTroops = new ArrayList<>();
    private List<Gear> activeGears = new ArrayList<>();
    private List<Gear> storageGears = new ArrayList<>();

    //COSTRUTTORI

    public PlayerDTOwAll(){}

    public PlayerDTOwAll(Player player) 
    {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        
        initDTO(player);
        activeTroops = filterByStatus(player, "active");
        storageTroops = filterByStatus(player, "storage");
    }

    private void initDTO(Player player)
    {
        if(player.troops.size()!= 0)
            for (Troop troop : player.troops) 
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

    public void switchTroopStatus(Integer troopId) 
    {
        TroopDTO troop = activeTroops.stream().filter(t -> t.getId().equals(troopId)).findFirst().orElse(null);
        if (troop != null) 
        {
            storageTroop(troop);
        } 
        else 
        {
            troop = storageTroops.stream()
                                    .filter(t -> t.getId().equals(troopId)).findFirst()
                                    .orElseThrow(() -> new RuntimeException("Troop not found"));

            if (activeTroops.size() >= 6) 
                throw new RuntimeException("Cannot have more than 6 active troops");

            activeTroop(troop);
        }
    }

    private void storageTroop(TroopDTO troop)
    {
        activeTroops.remove(troop);
        troop.setStatus("storage");
        storageTroops.add(troop);
    }

    private void activeTroop(TroopDTO troop)
    {
        storageTroops.remove(troop);
        troop.setStatus("active");
        activeTroops.add(troop);
    }

    private List<TroopDTO> filterByStatus(Player palyer, String status)
    {
        List<TroopDTO> res = palyer.troops.stream()
                                            .filter(t -> t.getStatus().equals(status)).map(t -> new TroopDTO(t)).toList();
        return res;
    }
}