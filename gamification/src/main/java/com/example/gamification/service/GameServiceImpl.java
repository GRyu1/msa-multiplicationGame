package com.example.gamification.service;

import com.example.gamification.client.MultiplicationResultAttemptClient;
import com.example.gamification.client.dto.MultiplicationResultAttempt;
import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService{
    public static final int LUCKY_NUMBER = 42;
    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;
    private MultiplicationResultAttemptClient attemptClient;

    GameServiceImpl(ScoreCardRepository scoreCardRepository,
                    BadgeCardRepository badgeCardRepository,
                    MultiplicationResultAttemptClient attemptClient) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
        this.attemptClient = attemptClient;
    }
    @Override
    public GameStats newAttemptForUser(Long playerId, Long attemptId, boolean correct) {
        if(correct){
            ScoreCard scoreCard = new ScoreCard(playerId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("Player = {} , Score = {} , answerId = {}",playerId,scoreCard.getScore(),attemptId);
            List<BadgeCard> badgeCards = processForBudges(playerId, attemptId);
            return new GameStats(playerId,scoreCard.getScore(),
                    badgeCards.stream()
                            .map(BadgeCard::getBadge)
                            .collect(Collectors.toList()));
        }
        return GameStats.emptyStats(playerId);
    }

    private List<BadgeCard> processForBudges(Long playerId, Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreForUser(playerId);
        log.info("Player = {} , Score = {}",playerId,totalScore);

        List<ScoreCard> scoreCardList = scoreCardRepository.findByPlayerIdOrderByScoreTimeStampDesc(playerId);
        List<BadgeCard> badgeCardList = badgeCardRepository.findByPlayerIdOrderByBadgeTimeStampDesc(playerId);
        CheckAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLCATOR,totalScore,100, playerId).ifPresent(badgeCards::add);
        CheckAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLCATOR,totalScore,500, playerId).ifPresent(badgeCards::add);
        CheckAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLCATOR,totalScore,999, playerId).ifPresent(badgeCards::add);

        if(scoreCardList.size() == 1 && !containsBadge(badgeCardList,Badge.FIRST_WON)){
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, playerId);
            badgeCards.add(firstWonBadge);
        }
        MultiplicationResultAttempt multiplicationResultAttempt = attemptClient.retrieveMultiplicationResultAttemptById(attemptId);
        if(!containsBadge(badgeCardList, Badge.LUCKY_NUMBER)&&
                (LUCKY_NUMBER == multiplicationResultAttempt.getMultiplicationFactorA()||
                        LUCKY_NUMBER == multiplicationResultAttempt.getMultiplicationFactorB())){
            BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER,playerId);
            badgeCards.add(luckyNumberBadge);
        }
        return badgeCards;
    }

    private boolean containsBadge(final List<BadgeCard> badgeCardList,final Badge badge) {
        return badgeCardList.stream().anyMatch(b->b.getBadge().equals(badge));
    }

    private BadgeCard giveBadgeToUser(Badge badge, Long playerId) {
        BadgeCard badgeCard = new BadgeCard(playerId, badge);
        log.info("사용자 ID={}, 새로운 배지 획득={}",playerId,badge);
        return badgeCard;
    }

    private Optional<BadgeCard> CheckAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCardList, Badge badge, int totalScore, int i, Long playerId) {
        return Optional.empty();
    }

    @Override
    public GameStats retrieveStatsForUser(Long playerId) {
        int score = scoreCardRepository.getTotalScoreForUser(playerId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByPlayerIdOrderByBadgeTimeStampDesc(playerId);
        return new GameStats(playerId, score,badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
    }
}
