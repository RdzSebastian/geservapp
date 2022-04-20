package com.estonianport.geservapp.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.estonianport.geservapp.service.impl.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// authentication manager
		auth.userDetailsService(usuarioService);
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.authorizeRequests()
//		.anyRequest().authenticated()
//		.and()
//		.formLogin()
//		.loginPage("/login")
//		.permitAll();
//	}
	
	@Bean 
	public static PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
    }

}
