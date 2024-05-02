package com.itb.lip2.academicologinmif3an.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/professor")
public class CursoControllerRest {
	
	
	
	@GetMapping("/cursos")
	String listarCursos () {
		
		return "Lista de Cursos";
		
	}
	

}
