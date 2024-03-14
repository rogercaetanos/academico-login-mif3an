package com.itb.lip2.academicologinmif3an.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itb.lip2.academicologinmif3an.model.Usuario;


@Controller
@RequestMapping("/academico")
public class AuthController {
	
	@GetMapping("/registration")
	public String showRegistrationForm(Usuario usuario, Model model) {
		
		model.addAttribute("user", usuario);
		
		return "registration";
		
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}

}
