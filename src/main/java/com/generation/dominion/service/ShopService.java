package com.generation.dominion.service;

import org.springframework.stereotype.Service;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.dto.TroopDTO;
import com.generation.dominion.dto.PlayerDTOwStorage;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Troop;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    private List<Gear> shopGears;
    private List<Troop> shopTroops;

    public ShopService() {
        shopGears = new ArrayList<>();
        shopTroops = new ArrayList<>();
        initMOCK();
    }

    private void initMOCK() {
        shopGears.add(new Gear(200, "spada", "una spada da infilare nel culo"));
        shopGears.add(new Gear(150, "scudo", "uno scudo da infilare nel culo"));

        shopTroops.add(new Troop("figther", 6, 11, 7, 300));
        shopTroops.add(new Troop("Tank", 5, 9, 13, 300));
    }

    public List<Gear> getShopGears() {
        return shopGears;
    }

    public List<Troop> getShopTroops() {
        return shopTroops;
    }

    public boolean buyGear(PlayerDTOwTroops player, String itemName) {
        for (Gear gear : shopGears) {
            if (gear.getName().equalsIgnoreCase(itemName)) {
                if (player.getGold() >= gear.getPrice() && player.addItemToInventory(gear)) {
                    player.buyGear(gear);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean buyTroop(PlayerDTOwTroops player, String troopName) {
        for (Troop troop : shopTroops) {
            if (troop.getClassName().equalsIgnoreCase(troopName)) {
                if (player.getGold() >= troop.getPrice()) {
                    player.getTroops().add(troop);
                    player.setGold(player.getGold() - troop.getPrice());
                    return true;
                }
            }
        }
        return false;
    }
}
