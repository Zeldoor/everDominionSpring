package com.generation.dominion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.dominion.dto.ChatDto;

import lombok.Data;

@Data
@Entity
public class Chat 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String date;

    public Chat(){}

    public Chat(ChatDto dto)
    {
        this.message = dto.getMessage();
        this.date = LocalDateTime.now().toString();
    }

    public LocalDateTime dateAsTime() 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        return LocalDateTime.parse(this.date, formatter);
    }
}
