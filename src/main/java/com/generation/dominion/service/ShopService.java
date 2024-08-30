package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.enums.E_Status;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Player_Gear;
import com.generation.dominion.model.Troop;
import com.generation.dominion.model.TroopInShop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.Player_GearRepository;
import com.generation.dominion.repository.TroopInShopRepository;
import com.generation.dominion.repository.TroopRepository;


import java.util.List;
@Service
public class ShopService 
{

    @Autowired
    private GearRepository gearRepository;

    @Autowired
    private TroopInShopRepository troopShopRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private Player_GearRepository p_gRepository;

    //nuova repo
    @Autowired
    private TroopRepository troopRepository;

    public List<GearDto> getShopGears() 
    {
        return gearRepository.findAll().stream().map(g -> new GearDto(g)).toList();
    }

    public List<TroopInShop> getShopTroops() 
    {
        return troopShopRepository.findAll();
    }

    public ResponseEntity<?> buyGear(PlayerDTO playerDto, Integer gearId) 
    {
        Player_Gear p_g = new Player_Gear();

        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        Gear gear = gearRepository.findById(gearId).get();

        if (player == null || gear == null) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player o Gear non trovato");

        if(player.getGears().stream().filter(g -> g.getGear().getId() == gear.getId()).toList().size() != 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Gear già comprato");

        p_g.setPlayerGear(player, gear);

        if (player.checkForBuy(gear.getPrice())) 
        {
            List<Player_Gear> allActivePlayerGear = p_gRepository.findAll().stream().filter(data -> data.getPlayer().getId() == player.getId() && data.getStatus().equalsIgnoreCase(E_Status.ACTIVE.toString())).toList();

            if(allActivePlayerGear.size() < 3)
                p_g.setStatus(E_Status.ACTIVE.toString());
            else
                p_g.setStatus(E_Status.STORAGE.toString());

            player.buyGear(p_g);
            playerRepository.save(player);

            return ResponseEntity.ok(player);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Oro insufficente");
    }

    public ResponseEntity<?> upgradeGear(PlayerDTO playerDto, Integer gearId)
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        Player_Gear pg = player.getGears().stream().filter(g -> g.getGear().getId().equals(gearId)).toList().get(0);

        if (player == null || pg == null) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore o Gear non trovato");

        if(pg.getTier() == 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Gear già al massimo");

        if (player.checkForBuy(pg.getGear().getPrice()*pg.getTier())) 
        {
            player.upgradeTier(pg);
            playerRepository.save(player);

            return ResponseEntity.ok(player);
        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Oro insufficente");
    }



    public void addStaminaToPlayer(int playerId) 
    {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Giocatore non trovato"));

        if(player.getStamina() >= 3)
            throw new RuntimeException("Stamina al massimo");

        if(player.getGold() >= 20)
        {
            player.addStamina(1);
            playerRepository.save(player);
        }
        else
        {
            throw new RuntimeException("Oro insufficente");
        }
    }


    public ResponseEntity<?> buyTroop(PlayerDTO playerDto, Integer TroopShopid) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        TroopInShop troopShop = troopShopRepository.findById(TroopShopid).get();

        if (player == null) 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player non esistente");

        if(player.checkForBuy(troopShop.getPrice()))
        {
            Troop troop = new Troop(troopShop);

            player.buyTroop(troop);
            playerRepository.save(player);

            return ResponseEntity.ok(player);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Oro insufficente");
    }



    public PlayerDTOwAll sellTroop(Integer troopId, Integer playerId) 
    {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Troop non trovata per questo player"));
        Troop troopToSell = player.getTroops().stream().filter(t -> t.getId().equals(troopId)).findFirst().orElseThrow(() -> new RuntimeException("Troop non trovata per questo player"));

        Integer sellPrice = (Integer) troopToSell.getPrice()/3;
        player.setGold(player.getGold() + sellPrice);
        player.getTroops().remove(troopToSell);
        troopRepository.deleteById(troopToSell.getId());
        playerRepository.save(player);

        return new PlayerDTOwAll(player);
    }

}