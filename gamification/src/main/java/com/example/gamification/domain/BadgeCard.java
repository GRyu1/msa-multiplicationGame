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
public final class BadgeCard {
    @Id
    @GeneratedValue
    @Column(name = "BADGE_ID")
    private final Long badgeId;

    private final Long playerId;
    private final long badgeTimeStamp;
    private final Badge badge;

    public BadgeCard(){
        this(null, null, 0, null);
    }
    public BadgeCard(final Long playerId , final  Badge badge){
        this(null,playerId,System.currentTimeMillis(),badge);
    }
}
