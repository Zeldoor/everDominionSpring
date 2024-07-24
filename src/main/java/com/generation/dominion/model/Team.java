package com.generation.dominion.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team")
public class Team 
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "troop1_id")
    private Troop troop1;

    @OneToOne
    @JoinColumn(name = "troop2_id")
    private Troop troop2;

    @OneToOne
    @JoinColumn(name = "troop3_id")
    private Troop troop3;

    @OneToOne
    @JoinColumn(name = "troop4_id")
    private Troop troop4;

    @OneToOne
    @JoinColumn(name = "troop5_id")
    private Troop troop5;

    @OneToOne
    @JoinColumn(name = "troop6_id")
    private Troop troop6;

    public boolean isAlive() 
    {
        return getAllTroops().stream().anyMatch(Troop::isAlive);
    }



    public Troop getAliveTroop() 
    {
        return getAllTroops().stream().filter(Troop::isAlive).findFirst().orElse(null);
    }



    public void performSpecialActions(Troop actingTroop) 
    {
        getAllTroops().forEach(troop -> troop.specialAction(actingTroop));
    }



    public List<Troop> getAllTroops() 
    {
        return List.of(troop1, troop2, troop3, troop4, troop5, troop6);
    }

    
}
