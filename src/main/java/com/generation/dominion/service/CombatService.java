package com.generation.dominion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Player_Gear;
import com.generation.dominion.repository.PlayerRepository;

@Service
public class CombatService 
{
    @Autowired
    private PlayerRepository playerRepository;

    private Integer[] rewardSystem(PlayerDTOwAll winner, PlayerDTOwAll loser) 
    {
        Integer[] loots = new Integer[2];
        Integer winBaseGold = 70;
        Integer loseBaseGold = 20;

        Integer winnerGold = (int)(Math.random()*30)+winBaseGold;
        Integer loserGold = (int)(Math.random()*20)+loseBaseGold;

        loots[0] = winnerGold;
        loots[1] = loserGold;

        winner.addGold(winnerGold);
        loser.removeGold(loserGold);

        return loots;
    }

    public FightResultDTO fightSystem(FightResultDTO FightDto)
    {
        FightResultDTO fightDtoRes = FightDto;

        Player pAttacker = playerRepository.findById(fightDtoRes.getAttacker().getId()).get();
        Player pDefender = playerRepository.findById(fightDtoRes.getDefender().getId()).get();

        PlayerDTOwAll attacker = new PlayerDTOwAll(pAttacker);
        PlayerDTOwAll defender = new PlayerDTOwAll(pDefender);

        attacker = gearEffects(attacker, pAttacker);
        defender = gearEffects(defender, pDefender);

        do
        {
            attacker.attack(defender);

            fightDtoRes.getResults().add(
                attacker.getNick()+" ha inflitto "+defender.getLastDmg()+" danni a "+defender.getNick()
                );
            fightDtoRes.getResults().add(
                defender.getNick()+" ha "+defender.getPlayerHealth()+" HP"
                );

            fightDtoRes.addEnemyHealth(defender.getPlayerHealth());


            if(defender.isDead())
                break;


            defender.attack(attacker);
            fightDtoRes.getResults().add(
                defender.getNick()+" ha inflitto "+attacker.getLastDmg()+" danni a "+attacker.getNick()
                );
            fightDtoRes.getResults().add(
            attacker.getNick()+" ha "+attacker.getPlayerHealth()+" HP"
            );

            fightDtoRes.addPlayerHeath(attacker.getPlayerHealth());
        } 
        while (attacker.isAlive() && defender.isAlive());


        if(attacker.isDead())
        {
            Integer[] loots = rewardSystem(defender, attacker);

            fightDtoRes.getResults().add(
                defender.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                attacker.getNick()+" ha PERSO "+loots[1]+" oro"
                );
        }
        else
        {
            Integer[] loots = rewardSystem(attacker, defender);

            fightDtoRes.getResults().add(
                attacker.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                defender.getNick()+" ha PERSO "+loots[1]+" oro"
                );
        }

        if(pAttacker.hasShield())
            pAttacker.removeShield();

        pDefender.applyShield();

        fightDtoRes.setAttacker(attacker);
        fightDtoRes.setDefender(defender);

        pAttacker.setGold(attacker.getGold());
        pDefender.setGold(defender.getGold());

        playerRepository.save(pAttacker);
        playerRepository.save(pDefender);

        return fightDtoRes;
    }

    private PlayerDTOwAll gearEffects(PlayerDTOwAll playerDto, Player player)
    {
        PlayerDTOwAll playerDtoUpgraded = playerDto;

        List<Player_Gear> activePG = player.getGears().stream().filter(g -> g.isActive()).toList();

        for (Player_Gear pg : activePG) 
        {
            playerDtoUpgraded = applyGearEffect(playerDtoUpgraded, pg);
        }

        return playerDtoUpgraded;
    }

    public PlayerDTOwAll applyGearEffect(PlayerDTOwAll playerDto, Player_Gear pg) 
    {
        switch (E_Gear.valueOf(pg.getGear().getName())) 
        { 
            case ANELLO:
                playerDto.setPlayerHealth(playerDto.getPlayerHealth() + (pg.getTier() * 10)); // Aumenta la salute in base al tier
                break;
            case BRACCIALE:
                playerDto.setPlayerMinDmg(playerDto.getPlayerMinDmg() + (pg.getTier() * 2)); // Aumenta il danno minimo
                break;
            case TIARA:
                playerDto.setPlayerMaxDmg(playerDto.getPlayerMaxDmg() + (pg.getTier() * 3)); // Aumenta il danno massimo
                break;
            case COLLANA: 
                playerDto.setGold(playerDto.getGold() + (pg.getTier() * 5)); // Aumenta l'oro ottenuto
                break;
        }

        return playerDto;
    }
}