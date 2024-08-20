package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.TroopInShop;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{
    @Autowired
    private ShopService shopService;

    @GetMapping("/gears")
    public List<GearDto> getShopGears() 
    {
        return shopService.getShopGears();
    }

    @GetMapping("/troops")
    public List<TroopInShop> getShopTroops() 
    {
        return shopService.getShopTroops();
    }

    // Compra Gear
    @PostMapping("/gear/{id}")
    public PlayerDTOwAll buyGear(@RequestBody PlayerDTO playerDto, @PathVariable int id) 
    {
        return shopService.buyGear(playerDto, id);
    }

    @PostMapping("/gear/upgrade/{id}")
    public PlayerDTOwAll upGear(@RequestBody PlayerDTO playerDto, @PathVariable int id) 
    {
        return shopService.upgradeGear(playerDto, id);
    }

    // Compra Troop
    @PostMapping("/troop/{id}")
    public PlayerDTOwAll buyTroop(@RequestBody PlayerDTO player, @PathVariable int id) 
    {
        return shopService.buyTroop(player, id);
    }
}
