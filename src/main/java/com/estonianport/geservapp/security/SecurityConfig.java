package com.estonianport.geservapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.estonianport.geservapp.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	UsuarioService usuarioService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// authentication manager (see below)
		
		auth.userDetailsService(usuarioService);
		 
//		auth.inMemoryAuthentication()
//		.withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//		.and()
//		.withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// http builder configurations for authorize requests and form login (see below)
//        super.configure(http);

//		http
//		.authorizeRequests()
//		.antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/").hasRole("USER")
//		.and().formLogin();
//	}

	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}

}
