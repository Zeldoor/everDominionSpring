package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.TroopRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class ShopService 
{

    @Autowired
    private GearRepository gearRepository;

    @Autowired
    private TroopRepository troopRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private List<Gear> shopGears;
    private List<Troop> shopTroops;

    @PostConstruct
    private void init() 
    {
        shopGears = gearRepository.findAll();
        shopTroops = troopRepository.findAll();
    }

    public List<Gear> getShopGears() 
    {
        return shopGears;
    }

    public List<Troop> getShopTroops() 
    {
        return shopTroops;
    }

    public Player buyGear(PlayerDTOwTroops playerDto, String itemName) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        if (player == null) 
        {
            throw new RuntimeException("Player non trovato");
        }

        for (Gear gear : shopGears) 
        {
            if (gear.getName().equalsIgnoreCase(itemName)) 
            {
                if (player.checkForBuy(gear.getPrice())) 
                {
                    player.buyGear(gear);
                    playerRepository.save(player);

                    return player;
                }
            }
        }

        throw new RuntimeException("Oro insufficente");
    }

    // public boolean buyTroop(PlayerDTOwTroops player, String troopName, boolean addToActive) {
    //     for (Troop troop : shopTroops) {
    //         if (troop.getClassName().equalsIgnoreCase(troopName)) {
    //             if (player.getGold() >= troop.getPrice()) {
    //                 if (addToActive) {
    //                     player.addActiveTroop(troop);
    //                 } else {
    //                     player.addTroopToStorage(troop);
    //                 }
    //                 player.setGold(player.getGold() - troop.getPrice());
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }
}