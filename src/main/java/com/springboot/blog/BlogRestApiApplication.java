package com.springboot.blog;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Blog App Rest API",
		description = "Spring boot rest API project ",
		version = "v1",
		contact = @Contact(
				name = "Nader Elattar",
				email = "naderalattar8eng@gmail.com"
		)

),externalDocs = @ExternalDocumentation(
		description = "Git repo for blog app rest API",
		url = "example.com"
)
)

public class BlogRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		insertRolesToDB();
		insertAdminUserToDB();
	}

	private void insertRolesToDB() {
		Role admin=new Role();
		admin.setName("ROLE_ADMIN");

		Role user=new Role();
		user.setName("ROLE_USER");
		try {
			roleRepository.findByName("ROLE_ADMIN").get();
		}
		catch (NoSuchElementException e){
			roleRepository.save(admin);
		}

		try {
			roleRepository.findByName("ROLE_USER").get();
		}
		catch (NoSuchElementException e){
			roleRepository.save(user);
		}



	}

	private void insertAdminUserToDB() {
		User adminUser=new User();
		adminUser.setName("admin");
		adminUser.setUsername("admin");
		adminUser.setEmail("admin@example.com");
		adminUser.setPassword(passwordEncoder.encode("12345"));

		Role adminRole=roleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->  new ResourceNotFoundException("ROLE" ,"NAME" ,"ROLE_ADMIN"));
		adminUser.setRoles(Set.of(adminRole));

		try {
			userRepository.findByUsernameOrEmail("admin","admin@example.com").get();
		}
		catch (NoSuchElementException e){
			userRepository.save(adminUser);
		}

	}
}
