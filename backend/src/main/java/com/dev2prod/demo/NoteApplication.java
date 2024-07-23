package com.dev2prod.demo;

import com.dev2prod.demo.domain.entities.PermissionEntity;
import com.dev2prod.demo.domain.entities.RoleEntity;
import com.dev2prod.demo.domain.entities.UserEntity;
import com.dev2prod.demo.domain.entities.enums.RoleEnum;
import com.dev2prod.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class NoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteApplication.class, args);
	}

}
