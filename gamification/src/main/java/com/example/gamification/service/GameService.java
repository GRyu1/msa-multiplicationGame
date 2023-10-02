package com.example.gamification.service;


import com.example.gamification.domain.GameStats;

public interface GameService {
    GameStats newAttemptForUser(Long playerId, Long attemptId , boolean correct);
    GameStats retrieveStatsForUser(Long playerId);
}