package com.generation.dominion.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.PvePlayer;
import com.generation.dominion.model.PveTroop;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PvePlayerRepository;
import com.generation.dominion.repository.PveTroopRepository;
import com.generation.dominion.repository.TroopRepository;
import com.generation.dominion.repository.PlayerRepository;

@Service
public class PveService 
{

    @Autowired
    private PvePlayerRepository pvePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PveTroopRepository pveTroopRepository;

    @Autowired
    private TroopRepository troopRepository;

    private Integer[] rewardSystem(Player winner, Player loser) 
    {
        Integer[] loots = new Integer[2];
        Integer winBaseGold = 70;
        Integer loseBaseGold = 20;

        Integer winnerGold = (int)(Math.random()*30) + winBaseGold;
        Integer loserGold = (int)(Math.random()*20) + loseBaseGold;

        loots[0] = winnerGold;
        loots[1] = loserGold;

        return loots;
    }

    public FightResultDTO fightSystem(FightResultDTO fightDto) 
    {
        FightResultDTO fightDtoRes = fightDto;

        Player attacker = playerRepository.findById(fightDtoRes.getAttacker().getId()).get();
        PvePlayer defender = pvePlayerRepository.findById(fightDtoRes.getDefender().getId()).get();

        List<Troop> attackerTroops = attacker.getTroops();
        List<PveTroop> defenderTroops = defender.getPveTroops();

        Random random = new Random();
        int numTroops = Math.min(6, attackerTroops.size()); // Massimo 6 truppe

        // Seleziona truppe casuali per il combattimento
        List<Troop> selectedAttackerTroops = attackerTroops.subList(0, numTroops);
        List<PveTroop> selectedDefenderTroops = defenderTroops.subList(0, Math.min(6, defenderTroops.size()));

        boolean attackerWins = false;

        // Combattimento tra le truppe
        for (int i = 0; i < Math.min(selectedAttackerTroops.size(), selectedDefenderTroops.size()); i++) {
            Troop attackerTroop = selectedAttackerTroops.get(i);
            PveTroop defenderTroop = selectedDefenderTroops.get(i);

            while (attackerTroop.getHealth() > 0 && defenderTroop.getHealth() > 0) {
                int attackDamage = random.nextInt(attackerTroop.getMaxDamage() - attackerTroop.getMinDamage() + 1) + attackerTroop.getMinDamage();
                int defendDamage = random.nextInt(defenderTroop.getMaxDamage() - defenderTroop.getMinDamage() + 1) + defenderTroop.getMinDamage();

                defenderTroop.setHealth(defenderTroop.getHealth() - attackDamage);
                fightDtoRes.getResults().add(attackerTroop.getClassName() + " infligge " + attackDamage + " danni a " + defenderTroop.getClassName());

                if (defenderTroop.getHealth() <= 0) {
                    fightDtoRes.getResults().add(defenderTroop.getClassName() + " è morto");
                    break;
                }

                attackerTroop.setHealth(attackerTroop.getHealth() - defendDamage);
                fightDtoRes.getResults().add(defenderTroop.getClassName() + " infligge " + defendDamage + " danni a " + attackerTroop.getClassName());

                if (attackerTroop.getHealth() <= 0) {
                    fightDtoRes.getResults().add(attackerTroop.getClassName() + " è morto");
                    break;
                }
            }
        }

        attackerWins = selectedAttackerTroops.stream().anyMatch(t -> t.getHealth() > 0);

        Integer[] loots;
        if (attackerWins) 
        {
            loots = rewardSystem(attacker, attacker); // L'attaccante vince e guadagna oro
            fightDtoRes.getResults().add(attacker.getNick() + " ha VINTO " + loots[0] + " oro");
            attacker.setGold(attacker.getGold() + loots[0]);
        } 
        else 
        {
            loots = rewardSystem(attacker, attacker); // L'attaccante perde il figth e l'oro
            fightDtoRes.getResults().add(attacker.getNick() + " ha PERSO " + loots[1] + " oro");
            attacker.setGold(attacker.getGold() + loots[1]);
        }

        playerRepository.save(attacker);

        return fightDtoRes;
    }
}
