package com.jay.blog.services;

import com.jay.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	void deleteComment(Integer commentId);
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
}

