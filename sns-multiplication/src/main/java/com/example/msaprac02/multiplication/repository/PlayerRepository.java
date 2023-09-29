package com.example.msaprac02.multiplication.repository;

import com.example.msaprac02.multiplication.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findByAlias(final String alias);
}
