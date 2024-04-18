package com.itb.lip2.academicologinmif3an.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itb.lip2.academicologinmif3an.model.Usuario;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final UsuarioService usuarioService;
	
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
		this.authenticationManager = authenticationManager;
		this.usuarioService = usuarioService;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authenticationToken);
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal(); // Pegando o usuário logado com sucesso
		
		Usuario userTodo = usuarioService.getUserByUsername(user.getUsername());
		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		
		String access_token = JWT.create().withSubject(userTodo.getId().toString())  
				          .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				          .withIssuer(request.getRequestURL().toString())
				          .withClaim("roles", 
				             user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				          .sign(algorithm);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
				          
		response.setContentType(APPLICATION_JSON_VALUE);		          
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	
	}
	
	
	
	
	

}
