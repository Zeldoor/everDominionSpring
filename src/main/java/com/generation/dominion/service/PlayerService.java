package com.generation.dominion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.enums.E_Player;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Player_Gear;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.Player_GearRepository;

@Service
public class PlayerService 
{
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GearRepository gearRepository;

    @Autowired
    Player_GearRepository playerGearRepository;

    public List<Player> getAllPlayers()
    {
        return playerRepository.findAll();
    }

    //Metodo gestionale
    public Troop switchSingleTroopState(Troop troop)
    {
        if(troop.isActive())
            troop.setStorage();
        else
            troop.setActive();

        return troop;
    }

    public Player_Gear switchSingleGearState(Player player, Gear gear) 
    {
        Player_Gear playerGear = player.getGears().stream().filter(pg -> pg.getGear().getName().equals(gear.getName())).toList().get(0);

        if(playerGear.isActive())
            playerGear.setStorage();
        else
            playerGear.setActive();

        return playerGear;
    }

    //Metodi attività
    public void updateLastActivity(int playerId) 
    {
        Player player = playerRepository.findById(playerId).get();

        player.updateLastActivity();
        player.setPlayerOnline();

        playerRepository.save(player);
    }

    public void playerOffline(int playerId) 
    {
        Player player = playerRepository.findById(playerId).get();

        player.updateLastActivity();
        player.setPlayerOffline();

        playerRepository.save(player);
    }


    //Metodi amici
    public List<Player> getOnlineFriends(int playerId) 
    {
        Player player = playerRepository.findById(playerId).get();
            
        return player.getOnlineFriends();
    }

    public List<Player> getFriends(int playerId) 
    {
        Player player = playerRepository.findById(playerId).get();
            
        return player.getFriends();
    }

    public Player addFriend(Integer id, Integer playerId)
    {
        Player player = playerRepository.findById(playerId).get();
        Player friend = playerRepository.findById(id).get();

        player.addFriend(friend);

        playerRepository.save(player);
        playerRepository.save(friend);

        return player;
    }
    
    //Metodo scudo
    public List<Player> getPlayersWithoutShield() 
    {
        return playerRepository.findAll().stream()
                .filter(player -> player.getShield() == null || player.getShield().equalsIgnoreCase(E_Player.NONE.toString()) || !player.hasShield())
                .collect(Collectors.toList());
    }
}