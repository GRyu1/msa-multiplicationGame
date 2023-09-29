package com.example.msaprac02.multiplication.service;

import com.example.msaprac02.multiplication.domain.Multiplication;
import com.example.msaprac02.multiplication.domain.MultiplicationResultAttempt;
import com.example.msaprac02.multiplication.domain.Player;
import com.example.msaprac02.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.msaprac02.multiplication.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MultiplicationServiceImplTest {
    private MultiplicationServiceImpl multiplicationService;
    @Mock
    private RandomGeneratorService randomGeneratorService;
    @Mock
    private MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;
    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService , multiplicationResultAttemptRepository , playerRepository);
    }
    @Test
    public void createRandomMultiplicationTest() {
        //given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50,30);

        //when
        Multiplication multiplication =multiplicationService.createRandomMultiplication();

        //then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true,false})
    public void checkCorrectAttemptTest(boolean correct) {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        Player player = new Player("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(player,multiplication,correct ? 3000 : 3001 ,correct);

        //when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        //then
        if(correct){
            assertThat(attemptResult).isTrue();
        }else {
            assertThat(attemptResult).isFalse();
        }

    }
}
