package com.example.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping()
    public String getMethodName(@RequestParam String param) {
        return "test message";
    }
}