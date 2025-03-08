package com.example.demo.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import com.example.demo.Repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // Test en entorno real con puerto aleatorio
@AutoConfigureMockMvc  // Configura MockMvc automáticamente
@ActiveProfiles("test") // Usa el perfil "test" para asegurarnos de que usa H2
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Limpia los datos antes de cada test
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // Limpia los datos después de cada test
    }

    @Test
    void shouldCreateAndFetchUser() throws Exception {
        String userJson = "{\"username\": \"newUser\"}";

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated());  // Verifica que se creó correctamente el usuario

        assertTrue(userRepository.findByUsername("newUser").isPresent());  // Comprueba que el usuario se guardó en la BD
    }
}
