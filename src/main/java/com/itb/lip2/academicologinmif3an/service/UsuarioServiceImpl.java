package com.itb.lip2.academicologinmif3an.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itb.lip2.academicologinmif3an.model.Papel;
import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public Usuario findByEmail(String email) {
		
		return usuarioRepository.findByEmail(email);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByEmail(username);
		if(usuario == null) {
			throw new UsernameNotFoundException("Nome de Usuário ou Senha Inválidos");
		}
		
		return new org.springframework.security.core.userdetails.User(usuario.getEmail(), 
				                                                       usuario.getPassword(), mapRolesToAuthorities(usuario.getPapeis()));
	}
	
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Papel> papeis) {
		
		return papeis.stream().map(papel -> new SimpleGrantedAuthority(papel.getNomePapel())).collect(Collectors.toList());
	}
	

}
