package com.itb.lip2.academicologinmif3an.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


import com.itb.lip2.academicologinmif3an.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		 return  new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		                        .antMatchers("/academico/registration**",
		                        		     "/academico/registration/**",
		                        		     "/js/**",
		                        		     "/css/**",
		                        		     "/img/**").permitAll()
		                         .and().authorizeRequests()
	                             .antMatchers(GET, "/usuarios/**").hasAnyAuthority("ROLE_USER")
	                             .anyRequest().authenticated()
	                             .and()
	                             .httpBasic()
	                             .and()
	                             .formLogin()
	                             .defaultSuccessUrl("/usuario/home", true)
	                             .loginPage("/academico/login").permitAll()
	                             .and()
	                             .logout()
	                             .invalidateHttpSession(true)
	                             .logoutRequestMatcher( new AntPathRequestMatcher("/academico/logout"))
	                             .logoutSuccessUrl("/academico/login?logout")
	                             .permitAll();
		
		              
	}
		
}
