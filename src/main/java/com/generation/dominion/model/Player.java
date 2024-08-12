package com.generation.dominion.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String online; 
    private LocalDateTime lastActivity;
    private String shield;


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
        this.online = "offline"; 
        this.lastActivity = LocalDateTime.now();
        this.shield = "none";
    }

    public Player(PlayerDTOwAll playerDto) 
    {
        this.nick = playerDto.getNick();
        this.gold = playerDto.getGold();
        this.stamina = playerDto.getStamina();
        this.online = "offline"; 
        this.lastActivity = LocalDateTime.now();
        this.shield = playerDto.getShield();
    }

    //SHOP METHODS

    public void buyGear(Gear gear)
    {
        detractGold(gear.getPrice());

        if(this.gears.size() < 6)
            this.gears.add(gear);
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
        System.out.println("SETTATO ONLINE");
        this.online = "online";
    }

    public void setPlayerOffline()
    {
        System.out.println("SETTATO OFFLINE");
        this.online = "offline";
    }

    public boolean isOnline()
    {
        return this.online.equals("online");
    }

    public boolean isOffline()
    {
        return this.online.equals("offline");
    }

    //Shield 
    public boolean hasShield()
    {
        if (this.shield == null || this.shield.equals("none")) 
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
        this.shield = "none";
    }

    public String getShieldDate()
    {
        if (this.shield.equals("none")) 
            return this.shield;

        LocalDateTime shieldEndTime = LocalDateTime.parse(this.shield, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return shieldEndTime.toLocalDate().toString();
    }

    public String getShieldTime()
    {
        if (this.shield.equals("none")) 
            return this.shield;

        LocalDateTime shieldEndTime = LocalDateTime.parse(this.shield, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return shieldEndTime.toLocalTime().toString();
    }

    public void activateShield() 
    {
        LocalDateTime shieldEndTime = LocalDateTime.now().plusHours(3); //qui dico che lo scudo dura tre ore
        this.shield = shieldEndTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


    public void applyShield()
    {
        this.shield = LocalDateTime.now().plusHours(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString();
    }

    public void  removeShield()
    {
        this.shield = "none";
    }
}