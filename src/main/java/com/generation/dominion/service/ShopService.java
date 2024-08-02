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

    public List<Gear> getShopGears() 
    {
        return gearRepository.findAll();
    }

    public List<TroopInShop> getShopTroops() 
    {
        return troopRepository.findAll();
    }

    public PlayerDTOwAll buyGear(PlayerDTO playerDto, Integer itemID) 
    {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        Gear g = gearRepository.findById(itemID).get();
        
        if (player == null) 
        {
            throw new RuntimeException("Player non trovato");
        }

        if (player.checkForBuy(g.getPrice())) 
        {
            player.buyGear(g);
            playerRepository.save(player);

            return new PlayerDTOwAll(player);
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