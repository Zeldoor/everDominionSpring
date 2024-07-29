package com.generation.dominion.model;


import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.dto.PlayerDTOwTroops;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "player")
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

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Gear> gear = new ArrayList<>(); // questi sono i gear attivi durante il figth


    public Player()
    {
        this.gold = 100;
        this.stamina = 3;
    }

    public Player(PlayerDTOwTroops playerDto)
    {
        this.nick = playerDto.getNick();
        this.gold = playerDto.getGold();
        this.stamina = playerDto.getStamina();
    }

}

   // @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   // private List<Troop> troopsInStorage = new ArrayList<>(); // queste sono le troop non attive che il giocatore conserva



   // @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   // private List<Gear> gearInStorage = new ArrayList<>(); // questi sono i gear non attivi che il giocatore conserva
