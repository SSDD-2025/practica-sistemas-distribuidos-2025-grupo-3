package com.example.demo.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.CommunityRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Community;
import com.example.demo.model.Post;
import com.example.demo.model.User;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @PostConstruct
    public void run() throws Exception{

        /* Usuario precargado del sistema para acceso invitado */
        userRepository.save(new User("Invitado", "password", "email", null));

        /* Informacióm de muestra */
        communityRepository.save(new Community("Java"));
        communityRepository.save(new Community("Futbol"));
        communityRepository.save(new Community("Baloncesto"));
        communityRepository.save(new Community("Aquagym"));

        userRepository.save(new User("Pitolo Margarito", "password", "email", null));
        userRepository.save(new User("Florensia", "password2", "email2", null));
        userRepository.save(new User("Pepito", "password3", "email3", null));
        userRepository.save(new User("Juan Carlos", "password4", "email4", null));
        userRepository.save(new User("Pepa", "password5", "email5", null));

        postRepository.save(new Post("Mi post sobre java", "Java mola que te cagas hermano, me la paso programando y aprendiendo cosas y en futuros post haré algo para enseñaros lo mucho que sé", "imagen", null, userRepository.findById(5L).get(), communityRepository.findById(1L).get()));
        postRepository.save(new Post("Aprendiendo Spring", "Spring es un framework muy poderoso para desarrollar aplicaciones en Java. Estoy disfrutando mucho aprenderlo.", "imagen3", null, userRepository.findById(2L).get(), communityRepository.findById(1L).get()));
        postRepository.save(new Post("Java Streams", "Los Streams en Java son una herramienta muy útil para manejar colecciones de datos de manera funcional.", "imagen4", null, userRepository.findById(3L).get(), communityRepository.findById(1L).get()));
        postRepository.save(new Post("Java y Concurrencia", "La concurrencia en Java es un tema complejo pero muy interesante. Estoy aprendiendo sobre hilos y sincronización.", "imagen5", null, userRepository.findById(4L).get(), communityRepository.findById(1L).get()));
        postRepository.save(new Post("Mi opinion", "Me encanta el futbol", "imagen2", null, userRepository.findById(2L).get(), communityRepository.findById(2L).get()));

    }
}
