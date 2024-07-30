package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{
    @Autowired
    private ShopService shopService;

    @Autowired
    private PlayerRepository playerRepo;


    // Mostra i Gear nello Shop
    @GetMapping("/gears")
    public List<Gear> getShopGears() 
    {
        return shopService.getShopGears();
    }

    // Ehm... si può cancellare?
    @GetMapping("/troops")
    public List<Troop> getShopTroops() 
    {
        return shopService.getShopTroops();
    }

    // Compra Gear
    @PostMapping("/buyGear")
    public PlayerDTOwTroops buyGear(@RequestBody PlayerDTOwTroops player, @RequestParam String itemName) 
    {
        Player p = shopService.buyGear(player, itemName);
        
        PlayerDTOwTroops playerDto = new PlayerDTOwTroops(p);

        return playerDto;
    }


    // Compra Troop
    @PostMapping("/buy/troop")
    public PlayerDTOwTroops buyTroop(@RequestBody PlayerDTOwTroops player, @RequestParam String troopName, @RequestParam boolean addToActive) 
    {
        boolean success = shopService.buyTroop(player, troopName, addToActive);
        if (success) {
            Player updatedPlayer = playerRepo.findById(player.getId()).orElse(null);
            return new PlayerDTOwTroops(updatedPlayer);
        } else {
            throw new IllegalArgumentException("Failed to purchase troop. Not enough gold or invalid troop name.");
        }
    }


}
