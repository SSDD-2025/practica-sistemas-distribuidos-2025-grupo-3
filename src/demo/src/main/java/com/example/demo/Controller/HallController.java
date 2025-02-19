package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Service.ComunityService;


@Controller
public class HallController {

    @Autowired
    private ComunityService cs;

    @GetMapping("/home")
    public String showHall(Model model) {
        model.addAttribute("comunities", cs.findAll());
        return "hall";
    }

}
