package com.generation.dominion.controller;

import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.websocket.WebSocketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ProvaSocket 
{
    @Autowired WebSocketService webSocketService;

    @GetMapping("/prova")
    public void getMethodName() 
    {
        webSocketService.sendMessage("calza", "che tristezza foratemi gli occhi");
    }

    
    @MessageMapping("/ricevitore")
    @SendTo("/topic/calza")
    public String messaggistica()
    {
        return "Controllo ritorno";
    }
}
