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
import java.util.stream.Collectors;


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

    //Gestione
    @PostMapping("/switch")
    public PlayerDTOwAll switchTroopStatus(@RequestBody int troopId) 
    {
        Troop troop = troopRepository.findById(troopId).get();
        
        playerServ.switchSingleState(troop);
        troopRepository.save(troop);

        
        Player player = playerRepository.findById(troop.getPlayer().getId()).get();

        return new PlayerDTOwAll(player);
    }

    //Attività
    @PostMapping("/{id}/heartbeat")
    public ResponseEntity<Void> updateLastActivity(@PathVariable int id) 
    {
        playerServ.updateLastActivity(id);

        return ResponseEntity.ok().build();
    }

    //Offline
    @GetMapping("/{id}/offline")
    public void setPlayerOffline(@PathVariable int id) 
    {
        playerServ.playerOffline(id);
    }

    //Online
    @GetMapping("/{id}/online-friends")
    public List<PlayerDTO> getOnlineFriends(@PathVariable int id) 
    {
        return playerServ.getOnlineFriends(id).stream().map(p -> new PlayerDTO(p)).toList();
    }

    //Amici online
    @GetMapping("/{id}/friends")
    public List<PlayerDTO> getFriends(@PathVariable int id) 
    {
        return playerServ.getFriends(id).stream().map(p -> new PlayerDTO(p)).toList();
    }

    //Aggiungi amici
    @PostMapping("/add/{id}")
    public PlayerDTOwAll getOnlineFriends(@PathVariable int id, @RequestBody Integer playerId) 
    {
        Player player = playerServ.addFriend(id, playerId);

        PlayerDTOwAll playerDtoUpdated = new PlayerDTOwAll(player);
        playerDtoUpdated.setFriends(player.getFriends().stream().map(f -> new PlayerDTO(f)).toList());

        return playerDtoUpdated;
    }

    //Tutti quelli senza scudo e che quindi possono essere attaccati
    @GetMapping("/without-shield")
    public List<PlayerDTOwAll> getPlayersWithoutShield() 
    {
        List<Player> playersWithoutShield = playerServ.getPlayersWithoutShield();
        return playersWithoutShield.stream().map(PlayerDTOwAll::new).collect(Collectors.toList());
    }


}

    //test
    // @GetMapping("/testino/{id}")
    // public void testino(@PathVariable int id) 
    // {
    //     Player p = playerRepository.findById(id).get();
    //     p.applyShield();
    //     playerRepository.save(p);
    // }


    
    // @GetMapping("/removino/{id}")
    // public void removino(@PathVariable int id) 
    // {
    //     Player p = playerRepository.findById(id).get();
    //     p.removeShield();
    //     playerRepository.save(p);
    // }