package com.generation.dominion.service;

import org.springframework.stereotype.Service;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Item;
import com.generation.dominion.model.Equipment;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService 
{
    private List<Item> shopItems;

    public ShopService() 
    {
        shopItems = new ArrayList<>();
        initializeShopItems();
    }

    private void initializeShopItems() 
    {
        shopItems.add(new Item("Pozione di vita", "Ripristina 1 life energy", 600));
        shopItems.add(new Equipment("Spada", "Aumenta l'attacco minimo del giocatore di 2", 300, "weapon"));
        shopItems.add(new Equipment("Scudo", "Aumenta la vita del giocatore di 5", 250, "armor"));
        // mettere altri item in futuro
    }

    public List<Item> getShopItems() 
    {
        return shopItems;
    }

    public boolean buyItem(PlayerDTOwTroops player, String itemName) 
    {
        for (Item item : shopItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (player.getGold() >= item.getPrice() && player.addItemToInventory(item)) 
                {
                    player.buyItem(item);
                    return true;
                }
            }
        }
        return false;
    }
}
