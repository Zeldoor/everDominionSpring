package com.generation.dominion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.generation.dominion.service.ShopService;
import com.generation.dominion.dto.PlayerDTOwTroops;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController 
{

    @Autowired
    private ShopService shopService;

    @GetMapping
    public List<Item> getShopItems() 
    {
        return shopService.getShopItems();
    }

    @PostMapping("/buy")
    public String buyItem(@RequestParam String playerName, @RequestParam String itemName) 
    {
        // metodo provvisorio per simulare fetch di un player dal nome
        PlayerDTOwTroops player = fetchPlayerByName(playerName);

        if (player == null) 
            return "Player not found.";

        boolean success = shopService.buyItem(player, itemName);
        return success ? "Purchase successful!" : "Purchase failed. Not enough gold or inventory full.";
    }

    // metodo provvisorio per simulare fetch di un player dal nome
    private PlayerDTOwTroops fetchPlayerByName(String playerName) 
    {
        // implementa fetch del player qua in futuro
        return new PlayerDTOwTroops(); // Placeholder
    }
}
