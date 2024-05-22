package com.itb.lip2.academicologinmif3an.filter;

import java.io.IOException;
import java.util.*;

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

	List<String> blackList = new ArrayList<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/v1/login")) {
			
			filterChain.doFilter(request, response); // Passar para o próximo filtro
		}else if(request.getServletPath().equals("/api/v1/logout")) {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String access_tokens = authorizationHeader.substring("Bearer ".length());
				// Removendo tokens antigos inválidos
				   removeTokenInvalidTheBlackList();

				// Toda vez que o usuário realizar o logout, adicionar o token na blackList "Lista negra"
				blackList.add(access_tokens);
			}
		}else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					// Verificar se o token ainda é valido, pois o usuário pode ter feito o logout antes do tempo expirar
					token = verifyInBlackList(token);
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
	
	private void removeTokenInvalidTheBlackList() {
		for(int i = 0; i < blackList.size();  i++) {

			try{
				Algorithm algorithm = Algorithm.HMAC256("secret" . getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(blackList.get(i)); // retorna uma exception, caso inválido
			}catch (Exception e) {
				blackList.remove(blackList.get(i));
			}
		}
	}

	private String verifyInBlackList(String token) throws Exception {

		for(int i = 0; i < blackList.size(); i++) {
			if(blackList.get(i).equals(token)){
				throw new Exception("Acesso Negado!");
			}
		}
		return token;
	}

}
