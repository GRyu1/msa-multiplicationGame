package com.example.gamification.event;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable {
    private final Long multiplicationResultAttemptId;
    private final Long playerId;
    private final boolean correct;
}
