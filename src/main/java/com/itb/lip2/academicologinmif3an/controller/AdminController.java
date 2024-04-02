package com.itb.lip2.academicologinmif3an.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;

@Controller
@RequestMapping("/academico/admin")
public class AdminController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	@GetMapping("/home")
	public String home() {
		
		Usuario usuario = usuarioService.getAuthenticatedUser();
		
		return "index-admin";
	}
	

}
