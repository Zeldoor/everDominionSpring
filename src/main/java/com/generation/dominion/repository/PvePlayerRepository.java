package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.PvePlayer;

public interface PvePlayerRepository extends JpaRepository<PvePlayer, Integer> 
{
    
}
