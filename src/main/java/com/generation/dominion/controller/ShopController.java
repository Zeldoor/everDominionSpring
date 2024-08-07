package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.TroopInShop;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{
    @Autowired
    private ShopService shopService;

    @GetMapping("/gears")
    public List<Gear> getShopGears() 
    {
        return shopService.getShopGears();
    }

    @GetMapping("/troops")
    public List<TroopInShop> getShopTroops() 
    {
        return shopService.getShopTroops();
    }

    // Compra Gear
    @PostMapping("/gear")
    public PlayerDTOwAll buyGear(@RequestBody PlayerDTO playerDto, @RequestParam Integer GearShopId) 
    {
        PlayerDTOwAll playerDtowTroops = shopService.buyGear(playerDto, GearShopId);

        return playerDtowTroops;
    }

    // Compra Troop
    @PostMapping("/troop/{id}")
    public PlayerDTOwAll buyTroop(@RequestBody PlayerDTO player, @PathVariable int id) 
    {
        PlayerDTOwAll playerDto = shopService.buyTroop(player, id);

        return playerDto;
    }
}
