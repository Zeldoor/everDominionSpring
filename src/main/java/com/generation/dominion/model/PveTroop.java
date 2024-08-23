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
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PveTroop
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                                        

    public String className;

    @Column(name = "min_damage")
    public Integer minDamage;

    @Column(name = "max_damage")
    public Integer maxDamage;

    @Column(name = "health")
    public Integer health;

    @ManyToOne
    @JoinColumn(name = "pve_player_id", nullable = true)
    @JsonIgnore
    PvePlayer pvePlayer;

    @Override
    public String toString() {
        return "Troop id=" + id + ", className=" + className + ", minDamage=" + minDamage + ", maxDamage=" + maxDamage
                + ", health=" + health + ", pvePlayer id: "+ pvePlayer.getId() ;
    }
}