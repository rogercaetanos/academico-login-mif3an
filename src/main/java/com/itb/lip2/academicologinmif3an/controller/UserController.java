package com.itb.lip2.academicologinmif3an.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itb.lip2.academicologinmif3an.model.Papel;
import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;


//@RestController // Controlador para API

@Controller     // Controlador para WEB
@RequestMapping("/academico/usuarios")
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/home")
	public String home() {
		
		Usuario usuario = usuarioService.getAuthenticatedUser();
		
		System.out.println("ID Usuário: " + usuario.getId());
		
		return "index-user";
	}
	
	
	@GetMapping("/living-room")
	public String livingRoom() {
		String home= "redirect:/academico/usuarios/home";
		
		Usuario usuario = usuarioService.getAuthenticatedUser();
		if(usuario.isCodStatusUsuario() == false) {
			return "redirect:/academico/usuarios/usuario-inativo";
		}
		
		String principalRole = usuario.getTipoPrincipalUsuario();
		Collection<Papel> papeis = usuario.getPapeis();
		
		for(Papel p : papeis) {
			if(p.getNomePapel().equals("ROLE_ADMIN") && principalRole.equals("ROLE_ADMIN")) {
				home = "redirect:/academico/admin/home";
			}else if(p.getNomePapel().equals("ROLE_USER") && principalRole.equals("ROLE_USER")) {
				home = "redirect:/academico/usuarios/home";
			}else if(p.getNomePapel().equals("ROLE_INSTRUCTOR") && principalRole.equals("ROLE_INSTRUCTOR")) {
				home = "redirect:/academico/professor/home";
			}else if(p.getNomePapel().equals("ROLE_STUDENT") && principalRole.equals("ROLE_STUDENT")) {
				home = "redirect:/academico/aluno/home";
			}
			
		}
			
		return home;
	}
	
	
	@GetMapping("/perfil")
	public String showPerfilForm(Model model) {
		
		Usuario usuario = usuarioService.getAuthenticatedUser();
        model.addAttribute("usuario", usuario);
		
		return "perfil";
	}
	
	
	@PostMapping("/perfil")
	public String updatePerfil(Usuario usuario) {
		
		Usuario usuarioDb = usuarioService.findByEmail(usuario.getEmail());
		
		usuarioDb.setBairro(usuario.getBairro());
		  
		
		
		return "redirect:/academico/usuarios/perfil";
	}
	
	
}
