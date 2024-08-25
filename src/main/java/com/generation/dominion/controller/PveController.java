package com.generation.dominion.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.dominion.repository.PvePlayerRepository;
import com.generation.dominion.service.PveService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.model.PvePlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<PvePlayer> getAllPvePlayers() 
    { 
        List<PvePlayer> pvePlayers = pveRepo.findAll();

        return pvePlayers;
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPvePlayer(@PathVariable Integer id) 
    {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PvePlayer");

        PvePlayer pvePlayer = pveRepo.findById(id).get();
        return ResponseEntity.ok(pvePlayer);
    }

    //testo figth pve
    @PostMapping("/fightPve")
    public ResponseEntity<?> fightPve(@RequestBody FightResultDTO dto) 
    {
        Optional<PvePlayer> pvePlayerOpt = pveRepo.findById(dto.getDefender().getId());

        if (pvePlayerOpt.isPresent()) 
        {
            FightResultDTO fightRes = pveService.fightSystem(dto);
            return ResponseEntity.ok(fightRes);
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PvePlayer not found");
        }
    }

}
