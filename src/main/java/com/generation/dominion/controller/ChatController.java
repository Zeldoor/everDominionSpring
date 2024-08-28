package com.generation.dominion.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.dto.ChatDto;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.model.Chat;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;

@RestController
@RequestMapping("/chat")
public class ChatController 
{
    @Autowired
    PlayerRepository playerRepo;

    @GetMapping
    public List<ChatDto> getAllChat()
    {
        List<ChatDto> res = new ArrayList<>();
        List<Player> players = playerRepo.findAll();

        for (Player player : players)
        {
            for (Chat c : player.getChats()) 
            {
                ChatDto dto = new ChatDto(c);
                dto.setPlayer(new PlayerDTO(player));

                res.add(dto);
            }
        }

        res.sort((chat1, chat2) -> chat2.dateAsTime().compareTo(chat1.dateAsTime()));
        
        return res;
    }


    @PostMapping
    public void insertChat(@RequestBody ChatDto dto)
    {
        Optional<Player> playerOpt = playerRepo.findById(dto.getPlayer().getId());

        if(playerOpt.isPresent())
        {
            Player player = playerOpt.get();

            Chat chat = new Chat(dto);
            player.addChat(chat);
    
            playerRepo.save(player);
        }
    }
}
