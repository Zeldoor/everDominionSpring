package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.TroopRepository;
import com.generation.dominion.service.CombatService;
import com.generation.dominion.service.PlayerService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/player")
public class PlayerController 
{

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerServ;
    @Autowired
    private CombatService combatServ;
    @Autowired
    private TroopRepository troopRepository;


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
    public FightResultDTO fight(@RequestBody FightResultDTO dto) 
    {
        FightResultDTO fightRes = dto;
        fightRes = combatServ.fightSystem(fightRes);

        return fightRes; 
    }

    @PostMapping("/switch")
    public PlayerDTOwAll switchTroopStatus(@RequestBody int troopId) 
    {
        Troop troop = troopRepository.findById(troopId).get();
        
        playerServ.switchSingleState(troop);
        troopRepository.save(troop);

        
        Player player = playerRepository.findById(troop.getPlayer().getId()).get();

        return new PlayerDTOwAll(player);
    }


    @PostMapping("/{id}/heartbeat")
    public ResponseEntity<Void> updateLastActivity(@PathVariable int id) 
    {
        playerServ.updateLastActivity(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/offline")
    public void setPlayerOffline(@PathVariable int id) 
    {
        playerServ.playerOffline(id);
    }


    @GetMapping("/{id}/online-friends")
    public List<PlayerDTO> getOnlineFriends(@PathVariable int id) 
    {
        return playerServ.getOnlineFriends(id).stream().map(p -> new PlayerDTO(p)).toList();
    }


    @GetMapping("/{id}/friends")
    public List<PlayerDTOwAll> getFriends(@PathVariable int id) 
    {
        return playerServ.getFriends(id).stream().map(p -> new PlayerDTOwAll(p)).toList();
    }


    @PostMapping("/add/{id}")
    public PlayerDTOwAll getOnlineFriends(@PathVariable int id, @RequestBody Integer playerId) 
    {
        Player player = playerServ.addFriend(id, playerId);

        PlayerDTOwAll playerDtoUpdated = new PlayerDTOwAll(player);
        playerDtoUpdated.setFriends(player.getFriends().stream().map(f -> new PlayerDTO(f)).toList());

        return playerDtoUpdated;
    }
}