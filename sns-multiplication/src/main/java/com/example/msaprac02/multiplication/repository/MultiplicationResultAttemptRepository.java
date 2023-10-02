package com.example.msaprac02.multiplication.repository;

import com.example.msaprac02.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {
    List<MultiplicationResultAttempt> findTop5ByPlayerAliasOrderByIdDesc(String userAlias);

    MultiplicationResultAttempt findOne(Long resultId);
}
