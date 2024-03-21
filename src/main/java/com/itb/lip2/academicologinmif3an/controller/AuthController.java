package com.itb.lip2.academicologinmif3an.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;


@Controller
@RequestMapping("/academico/auth")
public class AuthController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/registration")
	public String showRegistrationForm(Usuario usuario, Model model) {
		
		model.addAttribute("user", usuario);
		
		return "registration";
		
	}
	
	
	@PostMapping("/registration")
	public String registerUserAccount(Usuario usuario) {
		
		usuarioService.save(usuario);
		return "redirect:/academico/auth/registration?success";
	}
	
	
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	
	@ResponseBody
	@GetMapping(value= "/getSearchResult/{campo}/{valor}")
	public String getSearchResultAjax(@PathVariable("campo") String campo,
			                          @PathVariable("valor") String valor) {
		
		String msg = "";
		Usuario usuario = usuarioService.findByEmail(valor);
		if(usuario != null) {
			msg = "Email já existe, escolha um email válido!";
		}
		
		return msg;
	}

}
