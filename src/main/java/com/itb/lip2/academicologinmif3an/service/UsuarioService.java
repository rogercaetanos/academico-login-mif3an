package com.itb.lip2.academicologinmif3an.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.itb.lip2.academicologinmif3an.model.Papel;
import com.itb.lip2.academicologinmif3an.model.Usuario;

public interface UsuarioService extends UserDetailsService {
	
	Usuario findByEmail(String email);
	Usuario save(Usuario usuario);
	Usuario update(Usuario usuario);
	void addRoleToUser(String username, String roleName);
	Papel saveRole(Papel papel);
	Usuario getAuthenticatedUser();

}
