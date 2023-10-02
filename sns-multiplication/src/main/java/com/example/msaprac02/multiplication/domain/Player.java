package com.example.msaprac02.multiplication.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
public class Player {

    @Id
    @GeneratedValue
    @Column(name = "PLAYER_ID")
    private Long id;
    private final String alias;

    protected Player(){
        alias = null;
    }
}