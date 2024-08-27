package com.generation.dominion.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.generation.dominion.dto.ChatDto;
import com.generation.dominion.dto.NotifyDto;
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

    public void sendPlayerNotify(NotifyDto payload)
    {
        messagingTemplate.convertAndSend("/topic/notify/"+payload.getDefender().getId(), payload);
    }

    public void sendFullChat(List<ChatDto> payload)
    {
        messagingTemplate.convertAndSend("/topic/chat", payload);
    }
}
