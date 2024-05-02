package com.itb.lip2.academicologinmif3an.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.JWTVerifier;

import static java.util.Arrays.stream;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/v1/login")) {
			
			filterChain.doFilter(request, response); // Passar para o próximo filtro
		}else if(request.getServletPath().equals("/api/v1/logout")) { 
			
		}else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					
					String idUsuario = decodedJWT.getSubject();
					String[] papeis = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(papeis).forEach(papel-> {
						authorities.add(new SimpleGrantedAuthority(papel));
					} );
					
					UsernamePasswordAuthenticationToken authenticationToken = 
							  new UsernamePasswordAuthenticationToken(idUsuario, null,authorities);
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
					
				} catch (Exception e) {
					
					System.out.println("Error logging in: "  + e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
					
				}
				
			}else {
				filterChain.doFilter(request, response);
			}
			
		}
			
	}
	
	

}
