package com.generation.dominion.dto;

import com.generation.dominion.model.Player;
import com.generation.dominion.model.UserEntity;

import lombok.Data;

@Data
public class AuthResponseDto 
{
    private String accessToken;
    private UserEntity user;
    private PlayerDTOwAll playerDto;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken, UserEntity user, Player player) 
    {
        this.accessToken = accessToken;
        this.playerDto = new PlayerDTOwAll(player);
        this.user = user;
    }
}
