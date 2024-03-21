package com.itb.lip2.academicologinmif3an.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//@RestController // Controlador para API

@Controller     // Controlador para WEB
@RequestMapping("/academico/usuarios")
public class UserController {
	
	
	
	
	@GetMapping("/home")
	public String home() {
		
		return "index-user";
	}
	
	
	
}
