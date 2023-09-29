package com.example.msaprac02.multiplication.service;

import com.example.msaprac02.multiplication.domain.Multiplication;
import com.example.msaprac02.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {
    Multiplication createRandomMultiplication();
    boolean checkAttempt(final MultiplicationResultAttempt multiplicationResultAttempt);
    List<MultiplicationResultAttempt> getStatsForPlayer(String alias);
}
