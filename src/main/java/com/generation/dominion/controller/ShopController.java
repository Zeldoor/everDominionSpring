package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.GearDto;
import com.generation.dominion.dto.PlayerDTO;
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
    public ResponseEntity<?> buyGear(@RequestBody PlayerDTO playerDto, @PathVariable int id) 
    {
        return shopService.buyGear(playerDto, id);
    }

    @PostMapping("/upgrade/{id}")
    public ResponseEntity<?> upGear(@RequestBody PlayerDTO playerDto, @PathVariable int id)
    {
            return shopService.upgradeGear(playerDto, id);
    }
    
    // Compra Troop
    @PostMapping("/troop/{id}")
    public ResponseEntity<?> buyTroop(@RequestBody PlayerDTO player, @PathVariable int id) 
    {
            return shopService.buyTroop(player, id);
    }

    //compra stamina
    @PostMapping("/{id}/stamina")
    public ResponseEntity<?> addStamina(@PathVariable int id) 
    {
        try 
        {
            shopService.addStaminaToPlayer(id);
            return ResponseEntity.ok("");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Vende Troop
    @PostMapping("/sell/{id}")
    public ResponseEntity<?> sellTroop(@PathVariable Integer id, @RequestBody Integer playerId) 
    {
        try 
        {
            return ResponseEntity.ok(shopService.sellTroop(id, playerId));
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
