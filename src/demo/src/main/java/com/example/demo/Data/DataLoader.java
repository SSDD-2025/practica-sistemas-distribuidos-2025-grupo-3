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

        communityRepository.save(new Community("Java"));
        communityRepository.save(new Community("Futbol"));
        communityRepository.save(new Community("Baloncesto"));
        communityRepository.save(new Community("Aquagym"));

        userRepository.save(new User("Pitolo Margarito", "password", "email", null));
        userRepository.save(new User("Florensia", "password2", "email2", null));

        postRepository.save(new Post("Mi post sobre java", "Java mola que te cagas hermano, me la paso programando y aprendiendo cosas y en fuituros post haré algo para enseñaros lo mucho que sé", "imagen", null, userRepository.findById(1L).get(), communityRepository.findById(1L).get()));
        postRepository.save(new Post("Mi opinion", "Me encanta el futbol", "imagen2", null, userRepository.findById(2L).get(), communityRepository.findById(2L).get()));

    }
}
