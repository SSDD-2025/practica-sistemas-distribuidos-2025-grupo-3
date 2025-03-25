package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")  // Usa el perfil de pruebas con H2
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Mock  // Crea un mock del servicio para evitar dependencias externas
    private MockMvc mockMvc;

}
