package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Post;
import com.example.demo.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCommunityId(Long id);

    List<Post> findByOwner(User owner);

    List<Post> findByCommunityIdOrderByCreationDateDesc(Long id);

    Page<Post> findByCommunityIdOrderByCreationDateDesc(Long communityId, Pageable pageable);

    List<Post> findByOwnerOrderByCreationDateDesc(User owner);

    List<Post> findTop3ByOwnerOrderByCreationDateDesc(User user);

    List<Post> findTop5ByOrderByCreationDateDesc();

    Page<Post> findByOwnerOrderByCreationDateDesc(User user, Pageable pageable);

}
