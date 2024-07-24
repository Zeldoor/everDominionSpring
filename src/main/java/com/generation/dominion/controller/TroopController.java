package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.TroopDTO;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.TroopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/troop")
public class TroopController 
{
    @Autowired
    private TroopRepository troopRepository;

    @Autowired
    private PlayerRepository playerRepo;


    @PostMapping
    public Troop createTroop(@RequestBody TroopDTO troopDto) 
    { 
        Player player =  playerRepo.findById(troopDto.playerId).get();

        Troop troop = new Troop(troopDto, player);       
        return troopRepository.save(troop); 
    }


    @GetMapping
    public List<TroopDTO> getAllTroops() 
    { 
        List<TroopDTO> res = new ArrayList<>();
        List<Troop> troops = troopRepository.findAll();

        for (Troop troop : troops) 
        {
            res.add(new TroopDTO(troop));
        }
        return res;
    }


    @GetMapping("/{id}")
    public TroopDTO getTroop(@PathVariable int id) 
    {
        Troop troop = troopRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Troop not found"));
        return new TroopDTO(troop);
    }

    
}
