package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.generation.dominion.service.CombatService;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.model.Player;

@RestController
@RequestMapping("/combat")
public class CombatController 
{
    
    @Autowired
    private CombatService combatService;
    
    @Autowired
    private PlayerRepository playerRepository; // Aggiunta l'iniezione di PlayerRepository

    @PostMapping("/fight/{winnerId}/{loserId}")
    public void fight(@PathVariable int winnerId, @PathVariable int loserId) 
    {
        Player winner = playerRepository.findById(winnerId).orElseThrow(() -> new RuntimeException("Winner not found"));
        Player loser = playerRepository.findById(loserId).orElseThrow(() -> new RuntimeException("Loser not found"));
        combatService.handleCombat(winner, loser);
    }
}
