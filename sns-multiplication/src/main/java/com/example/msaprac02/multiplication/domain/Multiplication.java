package com.example.msaprac02.multiplication.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
public class Multiplication {
    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;
    private final int factorA;
    private final int factorB;

    Multiplication(){
        this(0,0);
    }

}
