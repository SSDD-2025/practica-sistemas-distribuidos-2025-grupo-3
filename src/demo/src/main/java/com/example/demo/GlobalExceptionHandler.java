package com.example.demo;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.demo.CustomExceptions.UserRegistrationProblemException;

//Catches errors that occur on the server before they can reach the controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationProblemException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserRegistrationProblemException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage())); // Devuelve JSON con mensaje
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String handleMaxSizeException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("errorCode", "413");
        model.addAttribute("errorMessage", "El archivo es demasiado grande. Límite permitido: 1MB.");
        return "error";
    }
}
