package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController 
{

    @Autowired
    private PlayerRepository playerRepository;


    @PostMapping
    public Player createPlayer(@RequestBody PlayerDTOwTroops playerDto) 
    { 
        Player player = new Player(playerDto);
        return playerRepository.save(player); 
    }


    @GetMapping
    public List<PlayerDTOwTroops> getAllPlayers() 
    { 
        List<PlayerDTOwTroops> res = new ArrayList<>();
        List<Player> players = playerRepository.findAll();

        for (Player player : players)
            res.add(new PlayerDTOwTroops(player));
        
        return res;
    }

       

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable int id) 
    {
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player not found")); 
    }



    // qua sotto il figth, che forse più in là avrà un controller suo

    // @PostMapping("/fight")
    // public FightResultDTO fight(@RequestBody List<Integer> playerIds) 
    // {
    //     FightResultDTO output = new FightResultDTO();

    //     if (playerIds.size() != 2) 
    //     {
    //         throw new IllegalArgumentException("Two player IDs must be provided.");
    //     }

    //     Player player1 = getPlayer(playerIds.get(0));
    //     Player player2 = getPlayer(playerIds.get(1));

    //     while (team1.isAlive() && team2.isAlive()) 
    //     {
    //         Troop troop1 = team1.getAliveTroop();
    //         Troop troop2 = team2.getAliveTroop();

    //         if (troop1.attack(troop2)) 
    //         {
    //             if (troop2.isDead()) 
    //             {
    //                 troop2 = team2.getAliveTroop();
    //             }
    //         }

    //         if (troop2 != null && troop2.attack(troop1)) 
    //         {
    //             if (troop1.isDead()) 
    //             {
    //                 troop1 = team1.getAliveTroop();
    //             }
    //         }

    //         team1.performSpecialActions(troop1);
    //         team2.performSpecialActions(troop2);
    //     }

    //     if (team1.isAlive()) 
    //     {
    //         output.setResult("Player " + player1.getNick() + " Won");
    //     } else if (team2.isAlive()) 
    //     {
    //         output.setResult("Player " + player2.getNick() + " Won");
    //     } else {
    //         output.setResult("Draw");
    //     }

    //     return output;
    // }
}