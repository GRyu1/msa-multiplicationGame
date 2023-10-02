package com.example.msaprac02.multiplication.service;

import com.example.msaprac02.multiplication.domain.Multiplication;
import com.example.msaprac02.multiplication.domain.MultiplicationResultAttempt;
import com.example.msaprac02.multiplication.domain.Player;
import com.example.msaprac02.multiplication.event.EventDispatcher;
import com.example.msaprac02.multiplication.event.MultiplicationSolvedEvent;
import com.example.msaprac02.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.msaprac02.multiplication.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MultiplicationServiceImpl implements MultiplicationService{
    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
    private PlayerRepository playerRepository;
    private EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     final MultiplicationResultAttemptRepository multiplicationResultAttemptRepository,
                                     final PlayerRepository playerRepository,
                                     final EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.multiplicationResultAttemptRepository = multiplicationResultAttemptRepository;
        this.playerRepository = playerRepository;
        this.eventDispatcher = eventDispatcher;
    }



    @Override
    public Multiplication createRandomMultiplication() {
        return new Multiplication(randomGeneratorService.generateRandomFactor(),randomGeneratorService.generateRandomFactor());
    }

    @Override
    @Transactional
    public boolean checkAttempt(MultiplicationResultAttempt multiplicationResultAttempt) {
        log.info("multiplicationResultAttempt={}",multiplicationResultAttempt);
        Optional<Player> player = playerRepository.findByAlias(multiplicationResultAttempt.getPlayer().getAlias());

        boolean correct =
                multiplicationResultAttempt.getResultAttempt() ==
                        multiplicationResultAttempt.getMultiplication().getFactorA() *
                                multiplicationResultAttempt.getMultiplication().getFactorB();

        Assert.isTrue(!multiplicationResultAttempt.isCorrect(),"채점한 상태로 보낼 수 없습니다.");

        MultiplicationResultAttempt attempt =
                new MultiplicationResultAttempt(
                        player.orElse(multiplicationResultAttempt.getPlayer()),
                        multiplicationResultAttempt.getMultiplication(),
                        multiplicationResultAttempt.getResultAttempt(), correct);
        multiplicationResultAttemptRepository.save(attempt);

        eventDispatcher.send(new MultiplicationSolvedEvent(
                attempt.getId(),
                attempt.getPlayer().getId(),
                attempt.isCorrect()
        ));

        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForPlayer(String alias) {
        return multiplicationResultAttemptRepository.findTop5ByPlayerAliasOrderByIdDesc(alias);
    }

    @Override
    public MultiplicationResultAttempt getResultById(final Long resultId) {
        return multiplicationResultAttemptRepository.findMultiplicationResultAttemptById(resultId);
    }
}