package com.example.demo.repository;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test") // Usa el perfil "test"
@DataJpaTest
public class UserRepositoryTest {


    @Test
    public static void testGuardarYRecuperarUsuario() {

    }
}
