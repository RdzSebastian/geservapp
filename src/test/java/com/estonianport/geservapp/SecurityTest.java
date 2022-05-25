package com.estonianport.geservapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.estonianport.geservapp.dao.RolDao;
import com.estonianport.geservapp.dao.UsuarioDao;
import com.estonianport.geservapp.model.Rol;
import com.estonianport.geservapp.model.Usuario;

@SpringBootTest
public class SecurityTest {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	RolDao rolDao;

//	@Test
//	void creacionUsuarioYRolSinJPA() {
//		Usuario u1 = new Usuario();
//		u1.setUsername("asd");
//		u1.setPassword(passwordEncoder().encode("asd"));
//		u1.setAccountNonExpired(true);
//		u1.setAccountNonLocked(true);
//		u1.setCredentialsNonExpired(true);
//		u1.setEnabled(true);
//
//		List<Rol> rs1 = new ArrayList<>();
//		Rol r1 = new Rol();
//		r1.setNombre("ADMIN");
//		rs1.add(r1);
//
//		u1.setRoles(rs1);
//		usuarioDao.save(u1);
//
//		Usuario u2 = new Usuario();
//		u2.setUsername("user1");
//		u2.setPassword(passwordEncoder().encode("user1Pass"));
//		u2.setAccountNonExpired(true);
//		u2.setAccountNonLocked(true);
//		u2.setCredentialsNonExpired(true);
//		u2.setEnabled(true);
//		
//		List<Rol> rs2 = new ArrayList<>();
//		Rol r2 = new Rol();
//		r2.setNombre("USER");
//		rs2.add(r2);
//		
//		u2.setRoles(rs2);
//		usuarioDao.save(u2);
//	}
	
//	@Test
//	void testRoles() {
//		Rol rol = new Rol();
//		rol.setNombre("ADMIN");
//		
//		rolDao.save(rol);
//		
//		rol = new Rol();
//		rol.setNombre("USER");
//		
//		rolDao.save(rol);
//	}
	
	@Test
	void testUsuarioAdmin() {
		Usuario u1 = new Usuario();
		u1.setUsername("asd");
		u1.setPassword(passwordEncoder().encode("asd"));
		u1.setNombre("juan");
		u1.setApellido("perez");
		u1.setMail("asd@gmail.com");
		u1.setAccountNonExpired(true);
		u1.setAccountNonLocked(true);
		u1.setCredentialsNonExpired(true);
		u1.setEnabled(true);
		
		List<Rol> roles = (List<Rol>) rolDao.findAll();
		Rol rolAdmin = null;
		for(Rol rol : roles) {
			if(rol.getNombre().equals("ADMIN")) {
				rolAdmin = rol;
			}
		}

		u1.setRol(rolAdmin);
//		usuarioDao.save(u1);

	}

	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
}
