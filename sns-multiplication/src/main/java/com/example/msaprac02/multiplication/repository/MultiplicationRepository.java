package com.example.msaprac02.multiplication.repository;

import com.example.msaprac02.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
