package com.example.msaprac02.multiplication.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class MultiplicationResultAttempt {
    @Id
    @GeneratedValue
    private Long id;
    //앞
    @ManyToOne(cascade = CascadeType.PERSIST) //뒤
    @JoinColumn(name = "PLAYER_ID")
    private final Player player;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;

    private final int resultAttempt;

    private final boolean correct;

    MultiplicationResultAttempt(){
        correct = false;
        player =null;
        multiplication=null;
        resultAttempt=-1;
    }
}