package com.jay.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jay.blog.entities.Category;
import com.jay.blog.entities.Post;
import com.jay.blog.entities.User;
import com.jay.blog.exceptions.ResourceNotFoundException;
import com.jay.blog.payloads.PostDto;
import com.jay.blog.payloads.PostResponse;
import com.jay.blog.repositories.CategoryRepository;
import com.jay.blog.repositories.PostRepository;
import com.jay.blog.repositories.UserRepository;
import com.jay.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setPostImageName("default.png");
		post.setPostAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = this.postRepository.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImageName(postDto.getPostImageName());
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepository.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Catgeory", "Id", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// TODO Auto -generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = this.postRepository.findAllByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepository.findByPostTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
