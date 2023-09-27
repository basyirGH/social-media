package com.basyir.projects.socialmedia.repository;

import com.basyir.projects.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
