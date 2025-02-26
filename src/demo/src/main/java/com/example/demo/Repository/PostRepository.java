package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCommunityId(Long id);

    List<Post> findByUserName(User userName);

}
