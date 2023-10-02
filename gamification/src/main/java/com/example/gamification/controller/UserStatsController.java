package com.example.gamification.controller;

import com.example.gamification.domain.GameStats;
import com.example.gamification.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class UserStatsController {
    private final GameService gameService;
    @GetMapping
    public GameStats getStatsForUser(@RequestParam("playerId") final Long playerId){
        return gameService.retrieveStatsForUser(playerId);
    }
}
