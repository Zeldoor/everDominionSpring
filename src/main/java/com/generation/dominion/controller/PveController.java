package com.generation.dominion.controller;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pve")
public class PveController 
{

    @Autowired 
    private PvePlayerRepository pveRepo;

    @GetMapping
    public List<PvePlayer> getAllPvePlayers() 
    { 
        List<PvePlayer> pvePlayers = pveRepo.findAll();

        return pvePlayers;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPvePlayer(@PathVariable Integer id) 
    {
        if (id)
        PvePlayer pvePlayer = playerOtp.get();
        return ResponseEntity.ok(pvePlayer);
    }
}
