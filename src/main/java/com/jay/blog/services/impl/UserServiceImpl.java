package com.jay.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jay.blog.config.AppConstants;
import com.jay.blog.entities.Role;
import com.jay.blog.entities.User;
import com.jay.blog.exceptions.ResourceNotFoundException;
import com.jay.blog.payloads.UserDto;
import com.jay.blog.payloads.UserResponse;
import com.jay.blog.repositories.RoleRepository;
import com.jay.blog.repositories.UserRepository;
import com.jay.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepository.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "ID", userId));
		return this.userToDto(user);
	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> pageUser = this.userRepository.findAll(pageable);
		List<User> users = pageUser.getContent();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		UserResponse userResponse = new UserResponse();
		userResponse.setContent(userDtos);
		userResponse.setPageNumber(pageUser.getNumber());
		userResponse.setPageSize(pageUser.getSize());
		userResponse.setTotalElements(pageUser.getTotalElements());
		userResponse.setTotalPages(pageUser.getTotalPages());
		userResponse.setLastPage(pageUser.isLast());
		return userResponse;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepository.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User newUser = this.userRepository.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
