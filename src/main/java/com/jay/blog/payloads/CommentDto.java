package com.jay.blog.payloads;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jay.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private int commentId;
	private String commentContent;
	
	@JsonManagedReference
	private UserDto user;
}
