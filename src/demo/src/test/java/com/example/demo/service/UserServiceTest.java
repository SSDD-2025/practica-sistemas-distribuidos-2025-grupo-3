/*package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class) // Extiende con Mockito para poder usar mocks
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenExists() {
        // Arrange - Configurar el mock
        User mockUser = new User(1L, "testUser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act - Llamar al método a probar
        User result = userService.getUserById(1L);

        // Assert - Verificar los resultados
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange - Configurar el mock para devolver vacío
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert - Verificar que se lanza la excepción esperada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(2L);
        });

        assertEquals("User not found", exception.getMessage()); // Ajusta el mensaje según lo que devuelve tu servicio

        verify(userRepository, times(1)).findById(2L);
    }
}
*/