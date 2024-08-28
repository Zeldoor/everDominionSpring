package com.generation.dominion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.dominion.model.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> 
{
    List<Chat> findByPlayerIdOrderByTimestampDesc(int playerId);
}
