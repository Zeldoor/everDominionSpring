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
import jakarta.transaction.Transactional;

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

   @Transactional
   public boolean buyGear(PlayerDTOwTroops playerDto, String itemName) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        if (player == null) 
        {
            return false;
        }

        for (Gear gear : shopGears) 
        {
            if (gear.getName().equalsIgnoreCase(itemName)) 
            {
                if (player.getGold() >= gear.getPrice()) 
                {
                    player.setGold(player.getGold() - gear.getPrice());
                    gear.setPlayer(player);
                    player.getGear().add(gear);

                    playerRepository.save(player);
                    gearRepository.save(gear);
                    return true;
                }
            }
        }
        return false;
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
