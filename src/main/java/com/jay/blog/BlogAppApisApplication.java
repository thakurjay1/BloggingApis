package com.jay.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jay.blog.config.AppConstants;
import com.jay.blog.entities.Role;
import com.jay.blog.repositories.RoleRepository;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	 public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Encoded password : "+this.passwordEncoder.encode("jay"));
		
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_USER");
			
			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepository.saveAll(roles);
			
			result.forEach((r)->{
				System.out.println(r);
			});
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
