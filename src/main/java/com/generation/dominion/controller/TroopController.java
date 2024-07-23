package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.TroopRepository;

import java.util.List;

@RestController
@RequestMapping("/troops")
public class TroopController {

    @Autowired
    private TroopRepository troopRepository;

    @GetMapping("/all")
    public List<Troop> getAllTroops() {
        return troopRepository.findAll();
    }
}
