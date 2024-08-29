package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.NotifyDto;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.websocket.WebSocketService;

@Service
public class CombatService 
{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private WebSocketService socket;

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
        PlayerDTOwAll defenderDto = new PlayerDTOwAll(defenderP);

        NotifyDto notify = new NotifyDto();
        notify.setAttacker(new PlayerDTO(attackerDto));
        notify.setDefender(new PlayerDTO(defenderDto));

        attackerP.useStamina();

        do
        {
            attackerDto.attack(defenderDto);

            fightDtoRes.getResults().add(
                attackerDto.getNick()+" ha inflitto "+defenderDto.getLastDmg()+" danni a "+defenderDto.getNick()
                );
            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha "+defenderDto.getPlayerHealth()+" HP"
                );

            fightDtoRes.addEnemyHealth(defenderDto.getPlayerHealth());


            if(defenderDto.isDead())
                break;


            defenderDto.attack(attackerDto);
            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha inflitto "+attackerDto.getLastDmg()+" danni a "+attackerDto.getNick()
                );
            fightDtoRes.getResults().add(
            attackerDto.getNick()+" ha "+attackerDto.getPlayerHealth()+" HP"
            );

            fightDtoRes.addPlayerHeath(attackerDto.getPlayerHealth());
        } 
        while (attackerDto.isAlive() && defenderDto.isAlive());


        if(attackerDto.isDead())
        {
            Integer[] loots = rewardSystem(defenderDto, attackerDto);

            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                attackerDto.getNick()+" ha PERSO "+loots[1]+" oro"
                );

            notify.setResult(attackerDto.getNick()+"ti ha attaccato, hai vinto "+loots[0]+" oro");
        }
        else
        {
            Integer[] loots = rewardSystem(attackerDto, defenderDto);

            fightDtoRes.getResults().add(
                attackerDto.getNick()+" ha VINTO "+loots[0]+" oro"
                );
            fightDtoRes.getResults().add(
                defenderDto.getNick()+" ha PERSO "+loots[1]+" oro"
                );
                
            notify.setResult(attackerDto.getNick()+"ti ha attaccato, hai perso "+loots[1]+" oro");
        }

        if(attackerP.hasShield())
            attackerP.removeShield();

        defenderP.applyShield();
        socket.sendPlayerNotify(notify);

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

}