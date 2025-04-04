package com.example.demo.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("errorCode", statusCode);

            switch (statusCode) {
                case 400 -> model.addAttribute("errorMessage", "Solicitud incorrecta.");
                case 401 -> model.addAttribute("errorMessage", "No autorizado.");
                case 403 -> model.addAttribute("errorMessage", "Acceso prohibido. No tienes privilegios para realizar esta acción");
                case 404 -> model.addAttribute("errorMessage", "Página no encontrada.");
                case 405 -> model.addAttribute("errorMessage", "Método no permitido. No reunes los requisitos para realziar esta acción.");
                case 408 -> model.addAttribute("errorMessage", "Tiempo de espera agotado.");
                case 409 -> model.addAttribute("errorMessage", "Conflicto en la solicitud.");
                case 500 -> model.addAttribute("errorMessage", "Error interno del servidor.");
                default -> model.addAttribute("errorMessage", "Ocurrió un error inesperado.");
            }
        }

        return "error";
    }
}