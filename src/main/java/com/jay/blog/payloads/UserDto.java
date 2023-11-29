package com.jay.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min = 3, message="Username must be min of 3 characters")
	private String name;
	
	@Email(message="Email Address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10, message="Password must be between 3 to 10 chars")
	private String password;
	
	@NotEmpty
	private String about;
	
	@JsonBackReference
	private List<CommentDto> comments = new ArrayList<CommentDto>();
	
	private Set<RoleDto> roles = new HashSet<RoleDto>();
}
