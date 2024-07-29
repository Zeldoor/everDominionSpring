package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.Gear;

public interface GearRepository extends JpaRepository<Gear, Integer> {
}
