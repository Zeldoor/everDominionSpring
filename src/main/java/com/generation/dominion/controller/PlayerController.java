package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Team;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/fight")
    public FightResultDTO fight(@RequestBody List<Integer> playerIds) {
        if (playerIds.size() != 2)
            throw new IllegalArgumentException("Two player IDs must be provided.");
        

        Player player1 = getPlayer(playerIds.get(0));
        Player player2 = getPlayer(playerIds.get(1));

        Team team1 = player1.getTeam();
        Team team2 = player2.getTeam();

        validateTeam(team1);
        validateTeam(team2);

        FightResultDTO output = new FightResultDTO();

        while (team1.isAlive() && team2.isAlive()) {
            Troop troop1 = team1.getAliveTroop();
            Troop troop2 = team2.getAliveTroop();

            if (troop1 != null && troop2 != null) {
                troop2.takeDamage(troop1.getMinDamage());

            if (troop2.isDead()) 
            {
                continue;
            }
                troop1.takeDamage(troop2.getMinDamage());
            }

            team1.performSpecialActions(troop1);
            team2.performSpecialActions(troop2);
        }

        if (team1.isAlive()) {
            output.setResult("Player 1 Team Won");
        } else {
            output.setResult("Player 2 Team Won");
        }

        return output;
    }

    private Player getPlayer(int playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isEmpty()) {
            throw new IllegalArgumentException("Player not found with ID: " + playerId);
        }
        return playerOpt.get();
    }

    private void validateTeam(Team team) {
        List<Troop> troops = team.getTroops();
        if (troops.size() < 1 || troops.size() > 6) {
            throw new IllegalArgumentException("Each team must have between 1 and 6 troops.");
        }
    }
}
