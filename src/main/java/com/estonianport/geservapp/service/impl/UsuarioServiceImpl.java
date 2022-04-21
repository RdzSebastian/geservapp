package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.UsuarioDao;
import com.estonianport.geservapp.model.Usuario;
import com.estonianport.geservapp.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long> implements UserDetailsService, UsuarioService{

	@Autowired
	UsuarioDao usuarioDao;

	@Override
	public CrudRepository<Usuario, Long> getDao() {
		return usuarioDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findUserByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
		return usuario;
	}
	
	public Usuario findUserByUsername(String username) {
		return usuarioDao.findUserByUsername(username);
	}
	
	public Long count() {
		return usuarioDao.count();
	}
}