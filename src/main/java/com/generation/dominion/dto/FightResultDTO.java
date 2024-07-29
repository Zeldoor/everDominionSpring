package com.generation.dominion.dto;

import java.util.List;

import lombok.Data;

@Data
public class FightResultDTO 
{
    private PlayerDTOwTroops attacker;
    private PlayerDTOwTroops defender;
    private List<String> results;
}
