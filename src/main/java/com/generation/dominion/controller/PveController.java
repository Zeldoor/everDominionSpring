package com.generation.dominion.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.dominion.repository.PvePlayerRepository;
import com.generation.dominion.service.PveService;

import com.generation.dominion.dto.PveFightResultDTO;
import com.generation.dominion.dto.PvePlayerDto;
import com.generation.dominion.model.PvePlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pve")
public class PveController 
{
    @Autowired
    private PvePlayerRepository pveRepo;
    @Autowired
    private PveService pveService;

    @GetMapping
    public List<PvePlayerDto> getAllPvePlayers() 
    { 
        List<PvePlayer> pvePlayers = pveRepo.findAll();

        return pvePlayers.stream().map(pve -> new PvePlayerDto(pve)).toList();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPvePlayer(@PathVariable Integer id) 
    {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PvePlayer");

        PvePlayer pvePlayer = pveRepo.findById(id).get();
        return ResponseEntity.ok(new PvePlayerDto(pvePlayer));
    }

    //testo figth pve
    @PostMapping("/fight/{id}")
    public ResponseEntity<?> fightPve(@PathVariable Integer id, @RequestBody Integer playerId) 
    {
        Optional<PvePlayer> pvePlayerOpt = pveRepo.findById(id);

        if (pvePlayerOpt.isPresent()) 
        {
            PveFightResultDTO fightRes = pveService.fightSystem(id, playerId);
            
            return ResponseEntity.ok(fightRes);
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PvePlayer not found");
        }
    }
}
