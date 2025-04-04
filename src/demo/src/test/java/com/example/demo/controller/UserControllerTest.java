package com.example.demo.controller;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")  // Usa el perfil de pruebas con H2
class UserControllerTest {


    @Mock  // Crea un mock del servicio para evitar dependencias externas
    private MockMvc mockMvc;

}
