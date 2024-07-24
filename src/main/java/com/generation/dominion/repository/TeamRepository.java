package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.dominion.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
