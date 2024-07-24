package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.Troop;

public interface TroopRepository extends JpaRepository<Troop, Integer> {

}