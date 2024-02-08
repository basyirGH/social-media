package com.basyir.projects.socialmedia.repository;

import com.basyir.projects.socialmedia.model.Post;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*If you don't have any custom repository functions to implement and 
you are satisfied with the query methods provided by Spring Data JPA 
for your entity, there's no strict requirement to create a custom 
repository interface. Spring Data JPA automatically provides 
basic CRUD operations and query methods based on naming 
conventions and method signatures. */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  
  @Cacheable("userPosts")
  List<Post> findPostsByUserId(long userId);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE Post p SET p.likesCount = ?2 WHERE p.id = ?1")
  void updateLikesCount(long postId, int likesCount);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE Post p SET p.repliesCount = ?2 WHERE p.id = ?1")
  void updateRepliesCount(long postId, int repliesCount);

  @Query("SELECT p FROM Post p ORDER BY p.dateAndTimePosted DESC")
  List<Post> findPostsOrderByDateAndTimePosted();

}