package com.example.gamification.event;

import com.example.gamification.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventHandler {
    private final GameService gameService;

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(final MultiplicationSolvedEvent event){
        log.info("Multiplication Solved Event : {}", event.getMultiplicationResultAttemptId());
        try {
            gameService.newAttemptForUser(event.getPlayerId(),
                    event.getMultiplicationResultAttemptId(),
                    event.isCorrect());
        }catch (final Exception e){
            log.error("MultiplicationSolvedEvent 처리 ERROR", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
