package com.generation.dominion.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotifyDto 
{
    private PlayerDTO attacker;
    private PlayerDTO defender;
    private String result;
    private LocalDateTime date = LocalDateTime.now();
}
