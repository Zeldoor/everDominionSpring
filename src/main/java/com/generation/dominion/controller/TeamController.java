package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.model.Team;
import com.generation.dominion.repository.TeamRepository;

@RestController
@RequestMapping("/team")
public class TeamController 
{


    @Autowired
    private TeamRepository teamRepository;


    @PostMapping("/create")
    public Team createTeam(@RequestBody Team team) { return teamRepository.save(team); }


    @GetMapping("/{id}")
    public Team getTeam(@PathVariable int id) 
    {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }
}
