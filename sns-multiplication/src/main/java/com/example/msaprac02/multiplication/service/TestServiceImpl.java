package com.example.msaprac02.multiplication.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService{

    public String fallbackMethod(Throwable t) {
        return "Fallback response: " + t.getMessage();
    }

    @Override
    @CircuitBreaker(name="TestCircuitBreaker" , fallbackMethod="fallbackMethod")
    public String callExternalService() {
        // External service call logic (simulate a failure)
        if (Math.random() > 0.5) {
            throw new RuntimeException("Service call failed");
        }
        return "Service call successful";
    }
}
