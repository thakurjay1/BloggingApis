package com.jay.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jay.blog.entities.User;
import com.jay.blog.exceptions.ResourceNotFoundException;
import com.jay.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//loading user from database by username
		User user = this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email", username));
		
		return user;
	}
	
}
