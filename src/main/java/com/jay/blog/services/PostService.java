package com.jay.blog.services;

import java.util.List;

import com.jay.blog.payloads.PostDto;
import com.jay.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get
	PostDto getPostById(Integer postId);
	
	//getAll
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get All posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get All posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	// search posts
	List<PostDto> searchPosts(String keyword);
}
