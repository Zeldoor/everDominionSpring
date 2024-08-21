package com.generation.dominion.dto;

import com.generation.dominion.enums.E_Gear;
import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player_Gear;

import lombok.Data;

@Data
public class GearDto 
{
    private Integer id;
    private String icon = "";
    private Integer price;
    private String name;
    private String description;
    private int tier;

    public GearDto(){}


    public GearDto(Player_Gear pg) 
    {
        
        this.id = pg.getGear().getId();
        this.icon = pg.getGear().getIcon();
        this.price = pg.getGear().getPrice();
        this.name = pg.getGear().getName();
        this.description = setDescriptionPlayerGear(pg);
        this.tier = pg.getTier();
    }

    public GearDto(Gear gear) 
    {
        this.id = gear.getId();
        this.icon = gear.getIcon();
        this.price = gear.getPrice();
        this.name = gear.getName();
        this.description = setDescriptionGear(gear);
        this.tier = 1;
    }

    private String setDescriptionPlayerGear(Player_Gear pg)
    {
        switch(E_Gear.valueOf(name))
        {
            case ANELLO:
                return "+"+10*pg.getTier()+" "+pg.getGear().getDescription();

            case PUGNALE:
                return "+"+4*pg.getTier()+" "+pg.getGear().getDescription();

            case PIUMA:
                return "+"+2*pg.getTier()+" "+pg.getGear().getDescription();

            case GETTONE:
                return "+"+5*pg.getTier()+" "+pg.getGear().getDescription();

            default: 
                return "NaN";
        }
    }

    private String setDescriptionGear(Gear gear)
    {
        switch(E_Gear.valueOf(name))
        {
            case ANELLO:
                return "+10 "+gear.getDescription();

            case PUGNALE:
                return "+4 "+gear.getDescription();

            case PIUMA:
                return "+2 "+gear.getDescription();

            case GETTONE:
                return "+5 "+gear.getDescription();

            default: 
                return "NaN";
        }
    }
}
