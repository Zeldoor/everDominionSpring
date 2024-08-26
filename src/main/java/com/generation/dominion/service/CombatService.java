package com.generation.dominion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;

@Service
public class CombatService 
{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Integer[] rewardSystem(PlayerDTOwAll winner, PlayerDTOwAll loser) 
    {
        Integer[] loots = new Integer[2];
        Integer winBaseGold = 70;
        Integer loseBaseGold = 20;

        Integer winnerGold = (int)(Math.random()*30)+winBaseGold;
        Integer loserGold = (int)(Math.random()*15)+loseBaseGold;

        loots[0] = winnerGold;
        loots[1] = loserGold;

        if(winner.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().size() > 0)
        {
            GearDto coin = winner.getActiveGears().stream().filter(g -> g.getName().equals(E_Gear.GETTONE.toString())).toList().get(0);
            winnerGold += coin.getTier() * 5;
        }

        winner.addGold(winnerGold);
        loser.removeGold(loserGold);

        return loots;
    }

    public FightResultDTO fightSystem(FightResultDTO FightDto)
    {
        FightResultDTO fightDtoRes = FightDto;

        Player attackerP = playerRepository.findById(fightDtoRes.getAttacker().getId()).get();
        Player defenderP = playerRepository.findById(fightDtoRes.getDefender().getId()).get();

        PlayerDTOwAll attackerDto = new PlayerDTOwAll(attackerP);
        PlayerDTOwAll defenderDto = gearEffects(new PlayerDTOwAll(defenderP));

        PlayerDTOwAll attackerDtoUpgraded = gearEffects(attackerDto);
        PlayerDTOwAll defenderDtoUpgraded = gearEffects(defenderDto);

        attackerP.useStamina();

        do
        {
            attackerDtoUpgraded.attack(defenderDtoUpgraded);

            fightDtoRes.getResults().add(
                attackerDtoUpgraded.getNick()+" ha inflitto "+defenderDtoUpgraded.getLastDmg()+" danni a "+defenderDtoUpgraded.getNick()
                );
            fightDtoRes.getResults().add(
                defenderDtoUpgraded.getNick()+" ha "+defenderDtoUpgraded.getPlayerHealth()+" HP"
                );

            fightDtoRes.addEnemyHealth(defenderDtoUpgraded.getPlayerHealth());


            if(defenderDtoUpgraded.isDead())
                break;


            defenderDtoUpgraded.attack(attackerDtoUpgraded);
            fightDtoRes.getResults().add(
                defenderDtoUpgraded.getNick()+" ha inflitto "+attackerDtoUpgraded.getLastDmg()+" danni a "+attackerDtoUpgraded.getNick()
                );
            fightDtoRes.getResults().add(
            attackerDtoUpgraded.getNick()+" ha "+attackerDtoUpgraded.getPlayerHealth()+" HP"
            );

            fightDtoRes.addPlayerHeath(attackerDtoUpgraded.getPlayerHealth());
        } 
        while (attackerDtoUpgraded.isAlive() && defenderDtoUpgraded.isAlive());


        if(attackerDtoUpgraded.isDead())
        {
            Integer[] loots = rewardSystem(defenderDto, attackerDto);

            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                attackerDtoUpgraded.getNick()+" ha PERSO "+loots[1]+" oro"
                );
        }
        else
        {
            Integer[] loots = rewardSystem(attackerDto, defenderDto);

            fightDtoRes.getResults().add(
                attackerDtoUpgraded.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha PERSO "+loots[1]+" oro"
                );
        }

        if(attackerP.hasShield())
            attackerP.removeShield();

        defenderP.applyShield();

        fightDtoRes.setAttacker(attackerDto);
        fightDtoRes.setDefender(defenderDto);

        attackerP.setGold(attackerDto.getGold());
        defenderP.setGold(defenderDto.getGold());

        playerRepository.save(attackerP);
        playerRepository.save(defenderP);

        // Invia la notifica al difensore con il risultato del combattimento
        sendCombatNotificationToDefender(defenderP.getId(), fightDtoRes);
        
        return fightDtoRes;
    }

    // Metodo helper per inviare la notifica
    
    private void sendCombatNotificationToDefender(int i, FightResultDTO fightResult) 
    {
        String destination = "/queue/combat-result/" + i;
        messagingTemplate.convertAndSend(destination, fightResult);
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