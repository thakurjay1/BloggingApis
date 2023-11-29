package com.jay.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jay.blog.entities.Category;
import com.jay.blog.entities.Post;
import com.jay.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findAllByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByPostTitleContaining(String title);
	
}
