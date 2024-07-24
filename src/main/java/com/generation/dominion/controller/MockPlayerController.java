package com.generation.dominion.controller;

import com.generation.dominion.dto.PlayerDTOwTroops;
import com.generation.dominion.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockPlayerController {

     @Autowired
     private MockService mockService;

     @GetMapping
     public List<PlayerDTOwTroops> getMockPlayers() {
         return mockService.generateMockPlayers(3);
     }
    
}
