package com.generation.dominion.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Player;
import com.generation.dominion.enums.E_Status;

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
    private String online; 
    private LocalDateTime lastActivity;
    private String shield;
    private String icon;


    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Troop> troops; // queste sono le troop attive che il giocatore usa nel figth

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player_Gear> gears = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable
    (
        name = "player_friends",
        joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private List<Player> friends = new ArrayList<>();

    public Player() 
    {
        this.gold = 100;
        this.stamina = 3;
        this.online = E_Player.OFFLINE.toString(); 
        this.lastActivity = LocalDateTime.now();
        this.shield = E_Player.NONE.toString();
    }

    public Player(PlayerDTOwAll playerDto) 
    {
        this.nick = playerDto.getNick();
        this.gold = playerDto.getGold();
        this.stamina = playerDto.getStamina();
        this.online = E_Player.OFFLINE.toString(); 
        this.lastActivity = LocalDateTime.now();
        this.shield = playerDto.getShield();
    }

    //SHOP METHODS

    public void buyGear(Player_Gear gear)
    {
        detractGold(gear.getGear().getPrice());
        this.gears.add(gear);
    }

    public void buyTroop(Troop troop)
    {
        detractGold(troop.getPrice());

        troop.setPlayer(this);
        troop.setStatus(this.troops.size() < 6 ? E_Status.ACTIVE.toString() : E_Status.STORAGE.toString());
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
    

    //FRIENDS + ACTIVITY METHODS


    public void updateLastActivity() 
    {
        this.lastActivity = LocalDateTime.now();
    }

    public boolean isActive() 
    {
        return this.isOnline() && this.lastActivity.isAfter(LocalDateTime.now().minusMinutes(5));
    }

    public List<Player> getOnlineFriends() 
    {
        List<Player> onlineFriends = new ArrayList<>();

        for (Player friend : this.friends) 
            if (friend.isActive()) 
                onlineFriends.add(friend);

        return onlineFriends;
    }

    public void addFriend(Player player)
    {
        this.friends.add(player);
        player.friends.add(this);
    }
    
    public void setPlayerOnline()
    {
        this.online = E_Player.ONLINE.toString();
    }

    public void setPlayerOffline()
    {
        this.online = E_Player.OFFLINE.toString();
    }

    public boolean isOnline()
    {
        return this.online.equalsIgnoreCase(E_Player.ONLINE.toString());
    }

    public boolean isOffline()
    {
        return this.online.equalsIgnoreCase(E_Player.OFFLINE.toString());
    }

    //Shield 
    public boolean hasShield()
    {
        if (this.shield == null || this.shield.equalsIgnoreCase(E_Player.NONE.toString())) 
            return false;

        LocalDateTime shieldEndTime = LocalDateTime.parse(this.shield, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return LocalDateTime.now().isBefore(shieldEndTime);
    }

    //AddShield 
    public void applyShield()
    {
        this.shield = LocalDateTime.now().plusHours(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString();
    }

    //removeShiedl
    public void  removeShield()
    {
        this.shield = E_Player.NONE.toString();
    }

    public String getShieldDate()
    {
        if (this.shield.equalsIgnoreCase(E_Player.NONE.toString())) 
            return this.shield;

        LocalDateTime shieldEndTime = LocalDateTime.parse(this.shield, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return shieldEndTime.toLocalDate().toString();
    }

    public String getShieldTime()
    {
        if (this.shield.equalsIgnoreCase(E_Player.NONE.toString())) 
            return this.shield;

        LocalDateTime shieldEndTime = LocalDateTime.parse(this.shield, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return shieldEndTime.toLocalTime().toString();
    }

    public void activateShield() 
    {
        LocalDateTime shieldEndTime = LocalDateTime.now().plusHours(3); //qui dico che lo scudo dura tre ore
        this.shield = shieldEndTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}