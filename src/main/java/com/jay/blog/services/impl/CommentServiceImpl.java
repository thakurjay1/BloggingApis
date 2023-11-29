package com.jay.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jay.blog.entities.Comment;
import com.jay.blog.entities.Post;
import com.jay.blog.entities.User;
import com.jay.blog.exceptions.ResourceNotFoundException;
import com.jay.blog.payloads.CommentDto;
import com.jay.blog.repositories.CommentRepository;
import com.jay.blog.repositories.PostRepository;
import com.jay.blog.repositories.UserRepository;
import com.jay.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepository.delete(comment);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto,Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		comment.setCommentContent(commentDto.getCommentContent());
		Comment updatedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

}
