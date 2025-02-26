package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    
}
