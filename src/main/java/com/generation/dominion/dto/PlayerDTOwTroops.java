package com.generation.dominion.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlayerDTOwTroops 
{
    private String nick;
    private int playerMinDmg;
    private int playerMaxDmg;
    private int playerHealth; 
    
    private int lifeEnergy;
    private int gold;
    
    private List<TroopDTO> troops;
}
