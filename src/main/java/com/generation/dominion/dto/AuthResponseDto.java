package com.generation.dominion.dto;

import com.generation.dominion.model.Player;
import com.generation.dominion.model.UserEntity;

import lombok.Data;

@Data
public class AuthResponseDto 
{
    private String accessToken;
    private String tokenType = "Bearer ";

    private UserEntity user;
    private PlayerDTOwAll playerDto;
    private String role;

    public AuthResponseDto(String accessToken, UserEntity user, Player player) 
    {
        this.accessToken = accessToken;

        this.user = user;
        this.playerDto = new PlayerDTOwAll(player);
        this.role = user.getRoles().get(0).getName();
    }
}
