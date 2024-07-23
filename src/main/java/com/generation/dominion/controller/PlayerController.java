package com.generation.dominion.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.TeamDTO;
import com.generation.dominion.dto.TroopDTO;
import com.generation.dominion.model.Team;
import com.generation.dominion.model.Troop;
import com.generation.dominion.model.subclass.Bard;
import com.generation.dominion.model.subclass.Fighter;
import com.generation.dominion.model.subclass.Healer;
import com.generation.dominion.model.subclass.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
public class PlayerController 
{

  /*  @PostMapping("/fight")
     public FightResultDTO fight(@RequestBody TeamDTO teamDto) 
    {
        List<Troop> playerTroops = teamDto.getTroops().stream()
                .map(this::createTroopFromDTO)
                .collect(Collectors.toList());
        Team playerTeam = new Team(playerTroops);

        List<Troop> mockTroops = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mockTroops.add(new Fighter()); 
        }
        Team mockTeam = new Team(mockTroops);

        FightResultDTO output = new FightResultDTO();

        while (playerTeam.isAlive() && mockTeam.isAlive()) 
        {
            Troop playerTroop = playerTeam.getTroops().stream().filter(Troop::isAlive).findFirst().orElse(null);
            Troop mockTroop = mockTeam.getTroops().stream().filter(Troop::isAlive).findFirst().orElse(null);

            if (playerTroop != null && mockTroop != null) 
            {
                mockTroop.takeDamage(playerTroop.getDamage());
                if (mockTroop.isDead()) 
                {
                    continue;
                }
                playerTroop.takeDamage(mockTroop.getDamage());
            }

            playerTeam.getTroops().forEach(troop -> troop.specialAction(playerTroop));
            mockTeam.getTroops().forEach(troop -> troop.specialAction(mockTroop));
        }

        if (playerTeam.isAlive()) 
        {
            output.setResult("Player Team Won");
        } else {
            output.setResult("Enemy Team Won");
        }

        return output;
    }
*/
    /*  private Troop createTroopFromDTO(TroopDTO dto) 
    {
        switch (dto.getRole().toLowerCase()) 
        {
            case "fighter":
                return new Fighter(dto.getDamage(), dto.getHealth());
            case "tank":
                return new Tank(dto.getDamage(), dto.getHealth());
            case "healer":
                return new Healer(dto.getDamage(), dto.getHealth());
            case "bard":
                return new Bard(dto.getDamage(), dto.getHealth());
            default:
                throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        }
  
        }
    */
}
