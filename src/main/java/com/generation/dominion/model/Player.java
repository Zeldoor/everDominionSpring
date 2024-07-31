package com.generation.dominion.model;


import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.dto.PlayerDTOwAll;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Player 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public String nick;
    public int stamina;
    public int gold;


    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Troop> troops; // queste sono le troop attive che il giocatore usa nel figth

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable
    (
        name = "player_gears",
        joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "gear_id", referencedColumnName = "id")
    )
    private List<Gear> gears = new ArrayList<>(); // questi sono i gear attivi durante il figth

    public Player()
    {
        this.gold = 100;
        this.stamina = 3;
    }

    public Player(PlayerDTOwAll playerDto)
    {
        this.nick = playerDto.getNick();
        this.gold = playerDto.getGold();
        this.stamina = playerDto.getStamina();
    }

    public void buyGear(Gear gear)
    {
        detractGold(gear.getPrice());

        if(this.gears.size() < 6)
            this.gears.add(gear);
    //     else
    //         this.storageGears.add(gear);
    }

    public void buyTroop(Troop troop)
    {
        detractGold(troop.getPrice());

        troop.setPlayer(this);
        troop.setStatus(this.troops.size() < 6 ? "active" : "storage");
        this.troops.add(troop);
    }

    
    public boolean checkForBuy(Integer ammount)
    {
        return this.gold >= ammount;
    }


    private void detractGold(Integer ammount)
    {
        this.gold -= ammount;
    }
}