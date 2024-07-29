package com.generation.dominion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> 
{
    Optional<Player> findByNick(String nick);
}
