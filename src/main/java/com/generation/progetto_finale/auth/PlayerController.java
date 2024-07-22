package com.generation.progetto_finale.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.auth.dto.FightResultDTO;
import com.generation.progetto_finale.auth.dto.TroopDTO;
import com.generation.progetto_finale.auth.model.Troop;

@RestController
@RequestMapping("/fight")
public class PlayerController 
{
    @PostMapping
    public FightResultDTO fight(@RequestBody TroopDTO dto)
    {
        Troop mock = new Troop(10, 10);
        Troop player = new Troop(dto.getDamage(), dto.getDefence());
        FightResultDTO output = new FightResultDTO();

        while (player.isAlive()) 
        {
            mock.takeDamage(player.getDamage());

            if(mock.isDead())
            {
                output.setResult("Player Won");
                break;
            }

            player.takeDamage(mock.getDamage());
            if(player.isDead())
            {
                output.setResult("Enemy Won");
                break;
            }
        }
        return output;
    }
}
