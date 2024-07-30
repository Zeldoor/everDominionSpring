package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.model.TroopInShop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.TroopInShopRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;
@Service
public class ShopService 
{

    @Autowired
    private GearRepository gearRepository;

    @Autowired
    private TroopInShopRepository troopRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private List<Gear> shopGears;
    private List<TroopInShop> shopTroops;

    @PostConstruct
    private void init() 
    {
        shopGears = gearRepository.findAll();
        shopTroops = troopRepository.findAll();
    }

    public List<Gear> getShopGears() 
    {
        return shopGears;
    }

    public List<TroopInShop> getShopTroops() 
    {
        return shopTroops;
    }

    public Player buyGear(PlayerDTO playerDto, String itemName) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        if (player == null) 
        {
            throw new RuntimeException("Player non trovato");
        }

        for (Gear gear : shopGears) 
        {
            if (gear.getName().equalsIgnoreCase(itemName)) 
            {
                if (player.checkForBuy(gear.getPrice())) 
                {
                    player.buyGear(gear);
                    playerRepository.save(player);

                    return player;
                }
            }
        }

        throw new RuntimeException("Oro insufficente");
    }



    public PlayerDTOwAll buyTroop(PlayerDTO playerDto, Integer TroopShopid) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        TroopInShop troopShop = troopRepository.findById(TroopShopid).get();

        if (player == null) 
        {
            throw new RuntimeException("Player non esistente");
        }

        if(player.checkForBuy(troopShop.getPrice()))
        {
            Troop troop = new Troop(troopShop);

            player.buyTroop(troop);
            playerRepository.save(player);

            return new PlayerDTOwAll(player);
        }

        throw new RuntimeException("Oro insufficente");
    }
}
/*
 * Le prossime cose da fare sono: 
 * Far si che Player possa avere massimo 6 Troop e 3 Gear durante il Fight
 * Creare uno Storage dove vengano salvate le Troop e i Gear che Player non ha equipaggiato
 * Salvere gli acquisti dello Shop nello Storage
 * Creare un metodo che faccia spostare al Player le Troop e i Gear dallo Storage al Player attivo e viceversa
 */