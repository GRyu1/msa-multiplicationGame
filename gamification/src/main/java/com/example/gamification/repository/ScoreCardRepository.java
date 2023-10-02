package com.example.gamification.repository;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {
    @Query("SELECT SUM(s.score) FROM com.example.gamification.domain.ScoreCard s WHERE s.playerId = :playerId GROUP BY s.playerId")
    int getTotalScoreForUser(@Param("playerId") final Long playerId);

    @Query("SELECT NEW com.example.gamification.domain.LeaderBoardRow(s.playerId, SUM(s.score)) " +
            "FROM com.example.gamification.domain.ScoreCard s " +
            "GROUP BY s.playerId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();

    List<ScoreCard> findByPlayerIdOrderByScoreTimeStampDesc (final Long playerId);

}
