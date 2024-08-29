package com.generation.dominion.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.generation.dominion.dto.ChatDto;
import com.generation.dominion.dto.PlayerDTO;
import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Chat;
import com.generation.dominion.model.Player;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.service.PlayerService;
import com.generation.dominion.websocket.WebSocketService;

@Component
public class WebSocketEmitter 
{
    @Autowired
    WebSocketService service;

    @Autowired
    PlayerService playerServ;
    @Autowired
    PlayerRepository playerRepo;
    

    // private Integer id;

    // @MessageMapping("/receiveId/{id}")
    // public void receiveId(@Payload String message, @DestinationVariable Integer id) 
    // {
    //     System.out.println("ID ricevuto da Angular: " + id);
    //     this.id = id;
    // }

    @Scheduled(fixedRate = 500)
    public void refreshPlayers()
    {
        List<Player> players = playerServ.getAllPlayers();
        List<PlayerDTOwAll> res = players.stream().map(p -> new PlayerDTOwAll(p)).toList();

        service.sendPlayersMessage(res);
    }

    @Scheduled(fixedRate = 500)
    public void fullChat()
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
        
        service.sendFullChat(res);
    }
    

    // @MessageMapping("/receiveId")
    // @SendTo("/topic/friends")
    // public Integer messaggistica(Integer id) 
    // {
    //     // Logica per gestire l'ID
    //     System.out.println("ID ricevuto: " + id);

    //     // Effettua eventuali altre operazioni...

    //     // Restituisce l'ID ricevuto o un altro valore
    //     return id;
    // }
}
