package com.example.demo.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.Repository.UserRepository;

@ActiveProfiles("test") // Usa el perfil "test"
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private static UserRepository userRepository;

    @Test
    public static void testGuardarYRecuperarUsuario() {

    }
}
