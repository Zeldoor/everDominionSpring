package com.generation.dominion.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class PvePlayer 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nick;
    private int gold;
    private String icon;


    @OneToMany(mappedBy = "pvePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<PveTroop> troops = new ArrayList<>();


    
}