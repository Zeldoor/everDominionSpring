package com.generation.dominion.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PveTroop
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                                         //NON TOCCARE

    public String className;

    @Column(name = "min_damage")
    public Integer minDamage;

    @Column(name = "max_damage")
    public Integer maxDamage;

    @Column(name = "health")
    public Integer health;

    public String status;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = true)
    @JsonIgnore
    PvePlayer pvePlayer;


    public PveTroop(){}

    public PveTroop(Integer damage, Integer health) 
    {
        this.minDamage = damage - 2;
        this.maxDamage = damage + 2;
        this.health = health;
    }






    @Override
    public String toString() {
        return "Troop id=" + id + ", className=" + className + ", minDamage=" + minDamage + ", maxDamage=" + maxDamage
                + ", health=" + health + ", status=" + status + ", Player id: "+ pvePlayer.getId() ;
    }


    
}