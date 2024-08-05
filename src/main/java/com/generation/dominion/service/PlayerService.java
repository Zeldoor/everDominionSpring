package com.generation.dominion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PlayerRepository;

@Service
public class PlayerService 
{
    @Autowired
    PlayerRepository playerRepository;

    public Troop switchSingleState(Troop troop)
    {
        if(troop.isActive())
            troop.setStorage();
        else
            troop.setActive();

        return troop;
    }

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
}