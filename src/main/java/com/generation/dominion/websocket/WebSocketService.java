package com.generation.dominion.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.PlayerDTOwAll;

@Service
public class WebSocketService 
{
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;

    public void sendLeadMessage(List<PlayerDTOwAll> payload)
    {
        messagingTemplate.convertAndSend("/topic/lead", payload);
    }

    public void sendPlayersMessage(List<PlayerDTOwAll> payload)
    {
        messagingTemplate.convertAndSend("/topic/players", payload);
    }
}
