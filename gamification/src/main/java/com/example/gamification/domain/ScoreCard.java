package com.example.gamification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class ScoreCard {
    public static final int DEFAULT_SCORE=10;

    @Id
    @GeneratedValue
    @Column(name = "CARD_ID")
    private final Long cardId;

    @Column(name = "PLAYER_ID")
    private final Long playerId;

    @Column(name = "ATTEMPT_ID")
    private final Long attemptId;

    @Column(name = "SCORE_TS")
    private final long scoreTimeStamp;

    @Column(name = "SCORE")
    private final int score;

    public ScoreCard(){
        this(null,null,null,0,0);
    }
    public ScoreCard(final Long playerId , final Long attemptId){
        this(null,playerId,attemptId,System.currentTimeMillis(),DEFAULT_SCORE);
    }
}
