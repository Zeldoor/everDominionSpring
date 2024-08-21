package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.PveTroop;

public interface PveTroopRepository extends JpaRepository<PveTroop, Integer> {
}
