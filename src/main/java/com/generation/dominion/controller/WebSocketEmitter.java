package com.generation.dominion.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.generation.dominion.dto.PlayerDTOwAll;
import com.generation.dominion.model.Player;
import com.generation.dominion.service.PlayerService;
import com.generation.dominion.websocket.WebSocketService;

@Component
public class WebSocketEmitter 
{
    @Autowired
    WebSocketService service;

    @Autowired
    PlayerService playerServ;

    // private Integer id;

    // @MessageMapping("/receiveId/{id}")
    // public void receiveId(@Payload String message, @DestinationVariable Integer id) 
    // {
    //     System.out.println("ID ricevuto da Angular: " + id);
    //     this.id = id;
    // }

    @Scheduled(fixedRate = 2000)
    public void refreshLead()
    {
        List<Player> players = playerServ.getPlayersWithoutShield();
        List<PlayerDTOwAll> res = players.stream().map(p -> new PlayerDTOwAll(p)).toList();

        service.sendLeadMessage(res);
    }

    @Scheduled(fixedRate = 2000)
    public void refreshPlayers()
    {
        List<Player> players = playerServ.getAllPlayers();
        List<PlayerDTOwAll> res = players.stream().map(p -> new PlayerDTOwAll(p)).toList();

        service.sendLeadMessage(res);
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
