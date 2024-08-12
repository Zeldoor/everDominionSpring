package com.generation.dominion.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService 
{
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    public void sendMessage(String topicSuffix,String payload)
    {
        messagingTemplate.convertAndSend("/topic/"+topicSuffix, payload);
    }
}
