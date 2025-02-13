package com.example.demo.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND) // Mantiene el código 404
    public String handleError(HttpServletRequest request) {
        return "error"; // Retorna la vista error.html en templates/
    }

}
