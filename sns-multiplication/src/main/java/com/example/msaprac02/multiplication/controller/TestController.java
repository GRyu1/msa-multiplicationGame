package com.example.msaprac02.multiplication.controller;

import com.example.msaprac02.multiplication.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/multi-test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping
    public String callService() {
        return testService.callExternalService();
    }
}
