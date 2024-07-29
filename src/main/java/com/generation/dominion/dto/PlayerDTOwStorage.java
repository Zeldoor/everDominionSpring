package com.generation.dominion.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.generation.dominion.model.Gear;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Troop;

@Data
public class PlayerDTOwStorage {
    //INFO
    private int id;
    private String nick;
    private int stamina;
    private int gold;

    //RISORSE
    private List<TroopDTO> troopsInStorage = new ArrayList<>();
    private List<Gear> gearInStorage = new ArrayList<>();

    //COSTRUTTORI

    public PlayerDTOwStorage() {}

    public PlayerDTOwStorage(Player player) {
        this.id = player.getId();
        this.nick = player.getNick();
        this.stamina = player.getStamina();
        this.gold = player.getGold();
        
        initDTO(player);
    }

    private void initDTO(Player player) {
        if(player.getTroopsInStorage().size() != 0) {
            for (Troop troop : player.getTroopsInStorage()) {
                TroopDTO dto = new TroopDTO(troop);
                this.troopsInStorage.add(dto);
            }
        }

        if(player.getGearInStorage().size() != 0) {
            this.gearInStorage.addAll(player.getGearInStorage());
        }
    }
}
/*
 * Per ora ho fatto un DTO a parte per vedere il player con ciò che non ha equipaggiato
 * Si può modificare più avanti per metterlo nel PlayerDTOwTroops ma sembrava troppo un mappazzone
 */