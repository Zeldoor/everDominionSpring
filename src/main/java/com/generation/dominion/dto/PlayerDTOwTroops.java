package com.generation.dominion.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlayerDTOwTroops 
{
    private String nick;
    private List<TroopDTO> troops;
    private int totalMinDamage;
    private int totalMaxDamage;
    private int totalHealth; 

    
}
