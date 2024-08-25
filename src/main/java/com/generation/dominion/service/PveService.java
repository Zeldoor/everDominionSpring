package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.dto.PveFightResultDTO;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.PvePlayer;
import com.generation.dominion.repository.PvePlayerRepository;
import com.generation.dominion.repository.PlayerRepository;

@Service
public class PveService 
{

    @Autowired
    private PvePlayerRepository pvePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public PveFightResultDTO fightSystem(Integer pveId, Integer playerId) 
    {
        PveFightResultDTO fightDtoRes = new PveFightResultDTO();
        Player player = playerRepository.findById(playerId).get();

        PlayerDTOwAll playerDto = new PlayerDTOwAll(player);
        PvePlayer pvePlayer = pvePlayerRepository.findById(pveId).get();

        do
        {
            playerDto.attack(pvePlayer);

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha inflitto "+pvePlayer.getLastDmg()+" danni a "+pvePlayer.getNick()
                );
            fightDtoRes.getResults().add(
                pvePlayer.getNick()+" ha "+pvePlayer.getPveHealth()+" HP"
                );

            fightDtoRes.addEnemyHealth(pvePlayer.getPveHealth());


            if(pvePlayer.isDead())
                break;


            pvePlayer.attack(playerDto);
            fightDtoRes.getResults().add(
                pvePlayer.getNick()+" ha inflitto "+playerDto.getLastDmg()+" danni a "+playerDto.getNick()
                );
            fightDtoRes.getResults().add(
            playerDto.getNick()+" ha "+playerDto.getPlayerHealth()+" HP"
            );

            fightDtoRes.addPlayerHeath(playerDto.getPlayerHealth());
        } 
        while (playerDto.isAlive() && pvePlayer.isAlive());


        if(playerDto.isAlive())
        {
            playerDto.setGold(playerDto.getGold() + pvePlayer.getGold());

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha VINTO "+pvePlayer.getGold()+" oro"
                );
        }
        else
        {
            playerDto.setGold(playerDto.getGold() - (pvePlayer.getGold()/2));

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha PERSO "+pvePlayer.getGold()/2+" oro"
                );
        }

        fightDtoRes.setPlayer(playerDto);

        player.setGold(playerDto.getGold());

        playerRepository.save(player);

        return fightDtoRes;
    }
}
