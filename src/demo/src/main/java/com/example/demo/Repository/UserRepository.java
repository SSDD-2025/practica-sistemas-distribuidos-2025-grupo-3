package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("SELECT COUNT(f) > 0 FROM User u JOIN u.friends f WHERE u.id = :currentUserId AND f.id = :otherUserId")
    boolean existsFriendship(@Param("currentUserId") Long currentUserId, @Param("otherUserId") Long otherUserId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.friends WHERE u.id = :currentUserId")
    User findByIdWithFriends(@Param("currentUserId") Long currentUserId);

}
