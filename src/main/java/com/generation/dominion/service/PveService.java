package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.dto.PveFightResultDTO;
import com.generation.dominion.dto.PvePlayerDto;
import com.generation.dominion.enums.E_Gear;
import com.generation.dominion.model.Player;
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
        PvePlayerDto pvePlayer = new PvePlayerDto(pvePlayerRepository.findById(pveId).get());

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
            Integer gold = pvePlayer.getGold();

            if(playerDto.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().size() > 0)
            {
                GearDto coin = playerDto.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().get(0);
                gold += coin.getTier() * 5;
            }

            playerDto.addGold(gold);

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha VINTO "+gold+" oro"
                );

            fightDtoRes.setGold(gold);
            fightDtoRes.setVictory(true);

        }
        else
        {
            playerDto.removeGold(pvePlayer.getGold()/2);

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha PERSO "+pvePlayer.getGold()/2+" oro"
                );

            fightDtoRes.setGold(pvePlayer.getGold()/2);
            fightDtoRes.setVictory(false);
        }

        fightDtoRes.setPlayer(playerDto);

        player.setGold(playerDto.getGold());

        playerRepository.save(player);

        return fightDtoRes;
    }
}
