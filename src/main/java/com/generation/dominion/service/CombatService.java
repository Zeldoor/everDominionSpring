package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.model.Player;

@Service
public class CombatService {
    @Autowired
    private PlayerRepository playerRepository;

    public void handleCombat(Player winner, Player loser) 
    {
        winner.addGold((int)(Math.random()*30)+70); //chi vince, 70+(0-30)
        loser.addGold((int)(Math.random()*20)+50);  //chi perde, 50+(0-20) gold

        loser.loseLifeEnergy();
        if (loser.isDead()) {
            System.out.println(loser.getNick() + " has died and can no longer play the game.");
            // altra possibile logica da implementare in futuro
        }

        playerRepository.save(winner);
        playerRepository.save(loser);
    }
}