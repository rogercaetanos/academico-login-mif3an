package com.itb.lip2.academicologinmif3an.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/v1/login")) {
			
			filterChain.doFilter(request, response); // Passar para o próximo filtro
		}else if(request.getServletPath().equals("/api/v1/logout")) { 
			
		}else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
		}
			
	}
	
	

}
