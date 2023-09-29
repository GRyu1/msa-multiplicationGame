package com.example.msaprac02.multiplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
public class RandomGeneratorServiceImplTest {
    private RandomGeneratorServiceImpl randomGeneratorServiceImpl;

    @BeforeEach
    public void setUp(){
        randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimit () throws Exception{
        //given
        List<Integer> randomFactor = IntStream.range(0,1000)
                .map(i->randomGeneratorServiceImpl.generateRandomFactor())
                .boxed().toList();
        //then
        assertThat(randomFactor).containsOnlyElementsOf(IntStream.range(11,100)
                .boxed().toList());
    }
}
