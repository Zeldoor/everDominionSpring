package com.generation.dominion.service;

import org.springframework.stereotype.Service;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.PlayerDTOwAll;

@Service
public class CombatService 
{

    public Integer[] rewardSystem(PlayerDTOwAll winner, PlayerDTOwAll loser) 
    {
        Integer[] loots = new Integer[2];
        Integer winBaseGold = 70;
        Integer loseBaseGold = 20;

        Integer winnerGold = (int)(Math.random()*30)+winBaseGold;
        Integer loserGold = (int)(Math.random()*20)+loseBaseGold;

        loots[0] = winnerGold;
        loots[1] = loserGold;
        winner.addGold(winnerGold); //chi vince, 70+(0-30)
        loser.addGold(loserGold);  //chi perde, 40+(0-20) 

        loser.loseStamina();
        
        return loots;
    }

    public FightResultDTO fightSystem(FightResultDTO FightDto)
    {
        FightResultDTO fightDtoRes = FightDto;
        PlayerDTOwAll attacker = fightDtoRes.getAttacker();
        PlayerDTOwAll defender = fightDtoRes.getDefender();

        do
        {
            attacker.attack(defender);
            fightDtoRes.getResults().add(
                attacker.getNick()+" ha inflitto "+defender.getLastDmg()+" danni a "+defender.getNick()
                );
            fightDtoRes.getResults().add(
                defender.getNick()+" ha "+defender.getPlayerHealth()+" HP"
                );


            if(defender.isDead())
                break;


            defender.attack(attacker);
            fightDtoRes.getResults().add(
                defender.getNick()+" ha inflitto "+attacker.getLastDmg()+" danni a "+attacker.getNick()
                );
            fightDtoRes.getResults().add(
            attacker.getNick()+" ha "+attacker.getPlayerHealth()+" HP"
            );
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

        fightDtoRes.setAttacker(attacker);
        fightDtoRes.setDefender(defender);

        return fightDtoRes;
    }
}