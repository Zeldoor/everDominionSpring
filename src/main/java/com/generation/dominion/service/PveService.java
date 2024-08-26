package com.generation.dominion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.dto.PveFightResultDTO;
import com.generation.dominion.enums.E_Gear;
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

        PlayerDTOwAll playerDtoUpgraded = gearEffects(playerDto);

        do
        {
            playerDtoUpgraded.attack(pvePlayer);

            fightDtoRes.getResults().add(
                playerDtoUpgraded.getNick()+" ha inflitto "+pvePlayer.getLastDmg()+" danni a "+pvePlayer.getNick()
                );
            fightDtoRes.getResults().add(
                pvePlayer.getNick()+" ha "+pvePlayer.getPveHealth()+" HP"
                );

            fightDtoRes.addEnemyHealth(pvePlayer.getPveHealth());


            if(pvePlayer.isDead())
                break;


            pvePlayer.attack(playerDtoUpgraded);
            fightDtoRes.getResults().add(
                pvePlayer.getNick()+" ha inflitto "+playerDtoUpgraded.getLastDmg()+" danni a "+playerDtoUpgraded.getNick()
                );
            fightDtoRes.getResults().add(
            playerDtoUpgraded.getNick()+" ha "+playerDtoUpgraded.getPlayerHealth()+" HP"
            );

            fightDtoRes.addPlayerHeath(playerDtoUpgraded.getPlayerHealth());
        } 
        while (playerDtoUpgraded.isAlive() && pvePlayer.isAlive());


        if(playerDtoUpgraded.isAlive())
        {
            Integer gold = pvePlayer.getGold();

            if(playerDto.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().size() > 0)
            {
                GearDto coin = playerDto.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().get(0);
                gold += coin.getTier() * 5;
            }

            playerDto.setGold(playerDto.getGold() + gold);

            fightDtoRes.getResults().add(
                playerDto.getNick()+" ha VINTO "+gold+" oro"
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

    private PlayerDTOwAll gearEffects(PlayerDTOwAll playerDto)
    {
        PlayerDTOwAll playerDtoUpgraded = playerDto;

        List<GearDto> activePG = playerDto.getActiveGears();

        for (GearDto pg : activePG) 
        {
            playerDtoUpgraded = applyGearEffect(playerDtoUpgraded, pg);
        }

        return playerDtoUpgraded;
    }

    public PlayerDTOwAll applyGearEffect(PlayerDTOwAll playerDto, GearDto gearDto) 
    {
        switch (E_Gear.valueOf(gearDto.getName())) 
        { 
            case ANELLO:
                playerDto.setPlayerHealth(playerDto.getPlayerHealth() + (gearDto.getTier() * 10)); // Aumenta la salute in base al tier
                break;
            case PUGNALE:
                playerDto.setPlayerMaxDmg(playerDto.getPlayerMaxDmg() + (gearDto.getTier() * 4)); // Aumenta il danno massimo
                break;
            case PIUMA:
                playerDto.setPlayerMinDmg(playerDto.getPlayerMinDmg() + (gearDto.getTier() * 2)); // Aumenta il danno minimo
                break;
            default:
                break;
        }

        return playerDto;
    }
}
