package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.TroopDTO;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.TroopRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/troop")
public class TroopController 
{
    @Autowired
    private TroopRepository troopRepository;


    @PostMapping("/create")
    public Troop createTroop(@RequestBody TroopDTO troopDto) 
    { 
        Troop troop = new Troop(troopDto);       
        return troopRepository.save(troop); 
    }


    @GetMapping("/all")
    public List<Troop> getAllTroops() { return troopRepository.findAll(); }


    @GetMapping("/{id}")
    public Troop getTroop(@PathVariable int id) 
    {
        Optional<Troop> troop = troopRepository.findById(id);
        if (troop.isEmpty()) 
        {
            throw new IllegalArgumentException("Troop not found with ID: " + id);
        }
        return troop.get();
    }

    
}
