package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/upgrade/{id}")
    public ResponseEntity<?> upGear(@RequestBody PlayerDTO playerDto, @PathVariable int id) 
    {
        try 
        {
            return ResponseEntity.ok(shopService.upgradeGear(playerDto, id));
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Compra Troop
    @PostMapping("/troop/{id}")
    public ResponseEntity<?> buyTroop(@RequestBody PlayerDTO player, @PathVariable int id) 
    {
        try 
        {
            return ResponseEntity.ok(shopService.buyTroop(player, id));
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
