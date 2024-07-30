package com.generation.dominion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;
import com.generation.dominion.repository.GearRepository;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.TroopRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Random;

@Service
public class ShopService 
{

    @Autowired
    private GearRepository gearRepository;

    @Autowired
    private TroopRepository troopRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private List<Gear> shopGears;
    private List<Troop> shopTroops;

    private Random random = new Random(); // serve a randomizzare le stats delle troop


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

    public List<Troop> getShopTroops() 
    {
        return shopTroops;
    }

    public Player buyGear(PlayerDTOwTroops playerDto, String itemName) 
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



    public boolean buyTroop(PlayerDTOwTroops playerDto, String troopName, boolean addToActive) {
        Player player = playerRepository.findById(playerDto.getId()).orElse(null);
        if (player == null || player.getGold() < 300) {
            return false; // Giocatore non trovato o non abbastanza oro
        }

        Troop troop = null;

        if (troopName.equalsIgnoreCase("archer")) {
            int minDamage = random.nextInt(4) + 6; // da 6 a 9
            int maxDamage = random.nextInt(4) + 10; // da 10 a 13
            int health = random.nextInt(3) + 7; // da 7 a 9
            troop = new Troop("archer", minDamage, maxDamage, health, 300);
        } else if (troopName.equalsIgnoreCase("tank")) {
            int minDamage = random.nextInt(5) + 2; // da 2 a 6
            int maxDamage = random.nextInt(3) + 7; // da 7 a 9
            int health = random.nextInt(5) + 10; // da 10 a 14
            troop = new Troop("tank", minDamage, maxDamage, health, 300);
        } else {
            return false; // Nome truppa non valido
        }

        troop.setPlayer(player); // Imposta il giocatore per la truppa

        if (addToActive) {
            player.getTroops().add(troop); // Aggiungi alle truppe attive del giocatore
        } else {
            System.out.println("acquisto fallito");
        }

        player.setGold(player.getGold() - 300);
        troopRepository.save(troop);
        playerRepository.save(player);

        return true;
    }
}
/*
 * Le prossime cose da fare sono: 
 * Far si che Player possa avere massimo 6 Troop e 3 Gear durante il Fight
 * Creare uno Storage dove vengano salvate le Troop e i Gear che Player non ha equipaggiato
 * Salvere gli acquisti dello Shop nello Storage
 * Creare un metodo che faccia spostare al Player le Troop e i Gear dallo Storage al Player attivo e viceversa
 */