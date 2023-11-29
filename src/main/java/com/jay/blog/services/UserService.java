package com.jay.blog.services;

import com.jay.blog.payloads.UserDto;
import com.jay.blog.payloads.UserResponse;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	void deleteUser(Integer userId);
}
