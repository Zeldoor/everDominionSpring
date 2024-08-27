package com.generation.dominion.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.generation.dominion.model.Chat;

import lombok.Data;

@Data
public class ChatDto 
{
    private PlayerDTO player;
    private String message;
    private String date;

    public ChatDto(){}

    public ChatDto(Chat chat)
    {
        this.message = chat.getMessage();
        this.date = chat.getDate();
    }

    public LocalDateTime dateAsTime() 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        return LocalDateTime.parse(this.date, formatter);
    }
}
