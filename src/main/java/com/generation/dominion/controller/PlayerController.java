package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.service.CombatService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController 
{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CombatService combatServ;


    // Crea un nuovo Player
    @PostMapping
    public Player createPlayer(@RequestBody PlayerDTOwAll playerDto) 
    { 
        Player player = new Player(playerDto);
        return playerRepository.save(player); 
    }


    //Legge tutti i Player con tutte le loro Troop e tutti i loro Gear
    @GetMapping
    public List<PlayerDTOwAll> getAllPlayers() 
    { 
        List<PlayerDTOwAll> res = new ArrayList<>();
        List<Player> players = playerRepository.findAll();

        for (Player player : players)
            res.add(new PlayerDTOwAll(player));
        
        return res;
    }


    // Legge un Player con tutte le sue Troop e tutti i suoi Gear
    @GetMapping("/{id}")
    public PlayerDTOwAll getPlayer(@PathVariable int id) 
    {
        Player player = playerRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        return new PlayerDTOwAll(player);
    }


    // test di combattimento
    @PostMapping("/fight")
    public FightResultDTO getPlayer(@RequestBody FightResultDTO dto) 
    {
        FightResultDTO fightRes = dto;

        fightRes = combatServ.fightSystem(fightRes);

        return fightRes; 
    }
}