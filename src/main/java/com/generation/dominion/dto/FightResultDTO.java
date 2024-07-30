package com.generation.dominion.dto;

import java.util.List;

import lombok.Data;

@Data
public class FightResultDTO 
{
    private PlayerDTOwAll attacker;
    private PlayerDTOwAll defender;
    private List<String> results;
}
