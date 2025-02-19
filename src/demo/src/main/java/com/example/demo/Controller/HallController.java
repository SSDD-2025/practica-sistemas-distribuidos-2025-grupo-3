package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HallController {
    @GetMapping("/home")
    public String showHall() {
        return "home";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
}
