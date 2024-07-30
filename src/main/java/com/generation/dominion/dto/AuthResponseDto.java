package com.generation.dominion.dto;

import com.generation.dominion.model.Player;

import lombok.Data;

@Data
public class AuthResponseDto 
{
    private String accessToken;
    private String role;
    private PlayerDTOwAll playerDto;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken, String role, Player player) 
    {
        this.accessToken = accessToken;
        this.playerDto = new PlayerDTOwAll(player);
        this.role = role;
    }
}
