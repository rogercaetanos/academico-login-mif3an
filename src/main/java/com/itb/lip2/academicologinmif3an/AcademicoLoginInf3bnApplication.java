package com.itb.lip2.academicologinmif3an;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.itb.lip2.academicologinmif3an.model.Papel;
import com.itb.lip2.academicologinmif3an.repository.PapelRepository;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;

@SpringBootApplication
public class AcademicoLoginInf3bnApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicoLoginInf3bnApplication.class, args);
	}
	
	
	@Bean
	CommandLineRunner run(UsuarioService usuarioService, PapelRepository papelRepository) {
		
		return args -> {
			if(papelRepository.findAll().size() == 0) {
				usuarioService.saveRole(new Papel("ROLE_USER"));
				usuarioService.saveRole(new Papel("ROLE_ADMIN"));
				usuarioService.saveRole(new Papel("ROLE_INSTRUCTOR"));
				usuarioService.saveRole(new Papel("ROLE_STUDENT"));
			}
		};
	}
	

}
