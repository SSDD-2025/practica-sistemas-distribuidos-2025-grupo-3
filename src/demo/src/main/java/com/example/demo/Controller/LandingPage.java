package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class LandingPage {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
}
