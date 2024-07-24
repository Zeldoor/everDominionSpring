package com.generation.dominion.service;

import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.dto.TroopDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockService {

   

     public List<PlayerDTOwTroops> generateMockPlayers(int numberOfPlayers) {
         List<PlayerDTOwTroops> players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
             PlayerDTOwTroops player = new PlayerDTOwTroops();
             player.setNick("Player" + (i + 1));
             player.setTroops(generateRandomTroopIds());
             player.setPlayerMinDmg(calculateTotalMinDamage(player.getTroops()));
             player.setPlayerMaxDmg(calculateTotalMaxDamage(player.getTroops()));
             player.setTotalHealth(calculateTotalHealth(player.getTroops()));
             players.add(player);
         }

         return players;
     }

     private List<TroopDTO> generateRandomTroopIds() 
     {
         List<TroopDTO> res = new ArrayList<>();
         for (int index = 0; index < 6; index++) 
         {
             res.add(TroopGenerator.generateRandomTroop());
         }
         return res;
        
     }

     private int calculateTotalMinDamage(List<TroopDTO> troops) {
         int totalDamage = 0;
         for (TroopDTO troop : troops) 
         {
            
             totalDamage += troop.getMinimumDamage() ; // danno minimo messo nel total damage
         }
         return totalDamage;
     }
    
     private int calculateTotalMaxDamage(List<TroopDTO> troops) {
         int totalDamage = 0;
         for (TroopDTO troop : troops) 
         {
            
             totalDamage += troop.getMinimumDamage() ; // danno minimo messo nel total damage
         }
         return totalDamage;
     }

     private int calculateTotalHealth(List<TroopDTO> troops) 
     {
         int totalHealth = 0;
         for (TroopDTO troop : troops) 
         {
            
             totalHealth += troop.getHealth() ; 
         }
         return totalHealth;
     }

}
