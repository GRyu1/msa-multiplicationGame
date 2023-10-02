package com.example.gamification.repository;

import com.example.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
    List<BadgeCard> findByPlayerIdOrderByBadgeTimeStampDesc(final Long playerId);
}
