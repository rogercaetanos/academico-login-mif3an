package com.itb.lip2.academicologinmif3an.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


import com.itb.lip2.academicologinmif3an.filter.CustomAuthenticationFilter;
import com.itb.lip2.academicologinmif3an.filter.CustomAuthorizationFilter;
import com.itb.lip2.academicologinmif3an.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Bean
	 BCryptPasswordEncoder passwordEncoder() {
		
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
		
		// API	
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), usuarioService);
		customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");		
		http.csrf().disable();
		// Término API

        http.authorizeRequests(requests -> requests
                .antMatchers("/academico/auth/registration**",
                        "/academico/auth/registration/**",
                        "/academico/auth/getSearchResult/**",
                      /*  "/api/**",*/
                        "/js/**",
                        "/css/**",
                        "/img/**").permitAll()).authorizeRequests(requests -> requests
                .antMatchers(GET, "/academico/usuarios/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(GET, "/academico/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(POST, "/academico/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(GET, "/academico/professor/**").hasAnyAuthority("ROLE_INSTRUCTOR")
                .antMatchers(GET, "/academico/aluno/**").hasAnyAuthority("ROLE_STUDENT")
                .antMatchers(GET, "/api/v1/professor/**").hasAnyAuthority("ROLE_INSTRUCTOR")
                
                .anyRequest().authenticated())
                .formLogin(login -> login
                        .defaultSuccessUrl("/academico/usuarios/living-room", true)
                        .loginPage("/academico/auth/login").permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/academico/auth/logout"))
                        .logoutSuccessUrl("/academico/auth/login?logout")
                        .permitAll());
        
        
        // API
           http.addFilter(customAuthenticationFilter);
           http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        // Término API
		
		              
	}
	
	
	// API

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {	
		return super.authenticationManagerBean();
	}
	
	// Término API
	
	
	
		
}
