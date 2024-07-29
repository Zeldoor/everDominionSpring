package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.PlayerRepository;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{
    @Autowired
    private ShopService shopService;

    @Autowired
    private PlayerRepository playerRepo;

    @GetMapping("/gears")
    public List<Gear> getShopGears() 
    {
        return shopService.getShopGears();
    }

    @GetMapping("/troops")
    public List<Troop> getShopTroops() 
    {
        return shopService.getShopTroops();
    }

    @PostMapping("/buyGear")
    public PlayerDTOwTroops buyGear(@RequestBody PlayerDTOwTroops player, @RequestParam String itemName) 
    {
        Player p = shopService.buyGear(player, itemName);
        
        PlayerDTOwTroops playerDto = new PlayerDTOwTroops(p);

        return playerDto;
    }
}









    // @PostMapping("/buy/gear")
    // public String buyGear(@RequestParam String playerName, @RequestParam String itemName) {
    //     // metodo provvisorio per simulare fetch di un player dal nome
    //     PlayerDTOwTroops player = fetchPlayerByName(playerName);

    //     if (player == null)
    //         return "Player not found.";

    //     boolean success = shopService.buyGear(player, itemName);
    //     return success ? "Purchase successful!" : "Purchase failed. Not enough gold or inventory full.";
    // }

    // @PostMapping("/buy/troop")
    // public String buyTroop(@RequestParam String playerName, @RequestParam String troopName, @RequestParam boolean addToActive) {
    //     // metodo provvisorio per simulare fetch di un player dal nome
    //     PlayerDTOwTroops player = fetchPlayerByName(playerName);

    //     if (player == null)
    //         return "Player not found.";

    //     boolean success = shopService.buyTroop(player, troopName, addToActive);
    //     return success ? "Purchase successful!" : "Purchase failed. Not enough gold.";
    // }

    // // metodo provvisorio per simulare fetch di un player dal nome
    // private PlayerDTOwTroops fetchPlayerByName(String playerName) {
    //     // implementa fetch del player qua in futuro
    //     return new PlayerDTOwTroops(); // Placeholder
    // }