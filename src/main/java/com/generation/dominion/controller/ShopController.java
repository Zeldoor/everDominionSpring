package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.TroopInShop;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{
    @Autowired
    private ShopService shopService;

    // Mostra i Gear nello Shop
    @GetMapping("/gears")
    public List<Gear> getShopGears() 
    {
        return shopService.getShopGears();
    }

    // Ehm... si può cancellare?
    @GetMapping("/troops")
    public List<TroopInShop> getShopTroops() 
    {
        return shopService.getShopTroops();
    }

    // Compra Gear
    @PostMapping("/buyGear")
    public PlayerDTOwAll buyGear(@RequestBody PlayerDTO playerDto, @RequestParam String itemName) 
    {
        Player player = shopService.buyGear(playerDto, itemName);
        
        PlayerDTOwAll playerDtowTroops = new PlayerDTOwAll(player);

        return playerDtowTroops;
    }

    // Compra Troop
    @PostMapping("/buy/troop")
    public PlayerDTOwAll buyTroop(@RequestBody PlayerDTO player, @RequestParam Integer TroopShopId) 
    {
        PlayerDTOwAll playerDto = shopService.buyTroop(player, TroopShopId);

        return playerDto;
    }
}
