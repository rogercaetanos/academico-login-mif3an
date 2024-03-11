package com.itb.lip2.academicologinmif3an.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.itb.lip2.academicologinmif3an.model.Usuario;

public interface UsuarioService extends UserDetailsService {
	 Usuario findByEmail(String email);

}
