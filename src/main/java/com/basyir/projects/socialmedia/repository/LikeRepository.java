package com.basyir.projects.socialmedia.repository;

import com.basyir.projects.socialmedia.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>{
}