package com.jay.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jay.blog.payloads.ApiResponse;
import com.jay.blog.payloads.UserDto;
import com.jay.blog.payloads.UserResponse;
import com.jay.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);
	}
	
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<UserResponse> getAllUsers(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = "id", required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required=false)String sortDir
			){
		UserResponse allUsers = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(allUsers);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
		UserDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}
}
