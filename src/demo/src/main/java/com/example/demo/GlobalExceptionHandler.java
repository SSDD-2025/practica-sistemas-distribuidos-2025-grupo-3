package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

//Catches errors that occur on the server before they can reach the controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String handleMaxSizeException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("errorCode", "413");
        model.addAttribute("errorMessage", "El archivo es demasiado grande. Límite permitido: 1MB.");
        return "error";
    }
}
