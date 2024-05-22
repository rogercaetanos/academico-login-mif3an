package com.itb.lip2.academicologinmif3an.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;


import com.itb.lip2.academicologinmif3an.model.Curso;
import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.service.CursoService;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/professor")
public class CursoControllerRest {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/cursos")
	public ResponseEntity<List<Curso>> listarCursos () {
		
		return ResponseEntity.ok().body(cursoService.findAll());
		
	}
	
	@PostMapping("/cursos")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Curso> salvarCurso (@RequestBody Curso curso) {

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/professor").toUriString());
		return ResponseEntity.created(uri).body(cursoService.save(curso));
		
	}
	
	
	@GetMapping("/cursos/{id}")
	public ResponseEntity<Curso> findById(@PathVariable(value="id") Long id) {
		
		
		return ResponseEntity.ok().body(cursoService.findById(id));
	}

}
