package com.generation.dominion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Status;
import com.generation.dominion.enums.E_Gear;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Player_Gear 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    Player player;

    @ManyToOne
    @JoinColumn(name = "gear_id")
    @JsonIgnore
    Gear gear;

    Integer tier = 1;
    String status;

    public void setPlayerGear(Player p, Gear g)
    {
        player = p;
        gear = g;
    }

    public void upgradeGearTier() {
        if (this.tier < 3) {
            this.tier++;
        } else {
            System.out.println("Upgrade massimo raggiunto.");
        }
    }
    
    public void applyGearEffect(PlayerDTOwAll playerDto) 
    {
        if (this.isActive()) 
        {
            switch (E_Gear.valueOf(gear.getName())) 
            { 
                case ANELLO:
                    playerDto.setPlayerHealth(playerDto.getPlayerHealth() + (tier * 10)); // Aumenta la salute in base al tier
                    break;
                case BRACCIALE:
                    playerDto.setPlayerMinDmg(playerDto.getPlayerMinDmg() + (tier * 2)); // Aumenta il danno minimo
                    break;
                case TIARA:
                    playerDto.setPlayerMaxDmg(playerDto.getPlayerMaxDmg() + (tier * 3)); // Aumenta il danno massimo
                    break;
                case COLLANA: 
                    playerDto.setGold(playerDto.getGold() + (tier * 5)); // Aumenta l'oro ottenuto
                    break;
            }
        }
    }

    public boolean isActive()
    {
        return this.status.equalsIgnoreCase(E_Status.ACTIVE.toString());
    }

    public void setActive()
    {
        status = E_Status.ACTIVE.toString();
    }

    public void setStorage()
    {
        status = E_Status.STORAGE.toString();
    }
}
