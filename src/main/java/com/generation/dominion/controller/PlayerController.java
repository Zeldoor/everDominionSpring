package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.dto.FightResultDTO;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.Player_GearRepository;
import com.generation.dominion.repository.TroopRepository;
import com.generation.dominion.service.CombatService;
import com.generation.dominion.service.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private GearRepository gearRepository;
    @Autowired
    private Player_GearRepository playerGearRepository;


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
    public ResponseEntity<?> getPlayer(@PathVariable Integer id) 
    {
        if(id != null)
        {
            Optional<Player> playerOtp = playerRepository.findById(id);

            if(playerOtp.isPresent())
            {
                Player player = playerOtp.get();
                return ResponseEntity.ok(new PlayerDTOwAll(player));
            }
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player "+id+" non trovato");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore");
        }
        
    

    // test di combattimento
    @PostMapping("/fight")
    public ResponseEntity<?> fight(@RequestBody FightResultDTO dto) 
    {
        Player enemy = playerRepository.findById(dto.getDefender().getId()).get();

        if(enemy.hasShield())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(enemy.getNick() + " ha uno scudo, non può essere attaccato");

        FightResultDTO fightRes = combatServ.fightSystem(dto);

        return ResponseEntity.ok(fightRes);
    }

    //Gestione
    @PostMapping("/switch")
    public PlayerDTOwAll switchTroopStatus(@RequestBody int troopId) 
    {
        Troop troop = troopRepository.findById(troopId).get();
        
        playerServ.switchSingleTroopState(troop);
        troopRepository.save(troop);
        
        Player player = playerRepository.findById(troop.getPlayer().getId()).get();

        return new PlayerDTOwAll(player);
    }

    @PostMapping("{id}/switch")
    public PlayerDTOwAll switchGearStatus(@RequestBody int gearId, @PathVariable int id) 
    {
        Player player = playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player non trovato"));
        Gear gear = gearRepository.findById(gearId).orElseThrow(() -> new IllegalArgumentException("Gear non trovato"));

        playerGearRepository.save(playerServ.switchSingleGearState(player, gear));
        player = playerRepository.findById(id).get();

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
    @PostMapping("/{id}/offline")
    public ResponseEntity<String> setPlayerOffline(@PathVariable int id) 
    {
        playerServ.playerOffline(id);
        
        return new ResponseEntity<>("Player offline", HttpStatus.OK);
    }

    //Online
    @GetMapping("/{id}/online-friends")
    public List<PlayerDTO> getOnlineFriends(@PathVariable int id) 
    {
        return playerServ.getOnlineFriends(id).stream().map(p -> new PlayerDTO(p)).toList();
    }

    //Amici online
    @GetMapping("/{id}/friends")
    public List<PlayerDTOwAll> getFriends(@PathVariable int id) 
    {
        return playerServ.getFriends(id).stream().map(p -> new PlayerDTOwAll(p)).toList();
    }

    //Aggiungi amici
    @PostMapping("/add/{id}")
    public ResponseEntity<?> addFriend(@PathVariable int id, @RequestBody Integer playerId) 
    {
        Player player = playerRepository.findById(playerId).get();
        Player friend = playerRepository.findById(id).get();

        if(player.getFriends().stream().filter(f -> f.getId() == id).toList().size() == 0)
        {
            player = playerServ.addFriend(player, friend);

            PlayerDTOwAll playerDtoUpdated = new PlayerDTOwAll(player);
            playerDtoUpdated.setFriends(player.getFriends().stream().map(f -> new PlayerDTO(f)).toList());

            return ResponseEntity.ok(playerDtoUpdated);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This player is already friend");
    }

    //Rimuovi amici
    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeFriend(@PathVariable int id, @RequestBody Integer playerId) 
    {
        Player player = playerRepository.findById(playerId) .orElseThrow(() -> new RuntimeException("Player not found"));
        Player friend = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Friend not found"));

        if (player.getFriends() != null && player.getFriends().stream().anyMatch(f -> f.getId() == id)) 
        {
            player = playerServ.removePlayer(player, friend);

            PlayerDTOwAll playerDtoUpdated = new PlayerDTOwAll(player);
            playerDtoUpdated.setFriends(player.getFriends().stream().map(f -> new PlayerDTO(f)).toList());

            return ResponseEntity.ok(playerDtoUpdated);
        }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Friend not found in player's friend list");
}

public Player removePlayer(Player player, Player friend) {
    player.removeFriend(friend);
    playerRepository.save(player);
    playerRepository.save(friend);
    return player;
}

    //Tutti quelli senza scudo e che quindi possono essere attaccati
    @GetMapping("/noShield")
    public List<PlayerDTOwAll> getPlayersWithoutShield() 
    {
        List<Player> playersWithoutShield = playerServ.getPlayersWithoutShield();
        
        return playersWithoutShield.stream().map(p -> new PlayerDTOwAll(p)).collect(Collectors.toList());
    }

    @PostMapping("{id}/icon")
    public void switchIcon(@RequestBody String newIcon, @PathVariable int id) 
    {
        Player player = playerRepository.findById(id).get();
        player.setIcon(newIcon);
        playerRepository.save(player);
    }
}