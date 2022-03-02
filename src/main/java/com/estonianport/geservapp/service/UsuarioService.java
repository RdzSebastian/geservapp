package com.estonianport.geservapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.dao.UsuarioDao;
import com.estonianport.geservapp.model.Usuario;

@Service
public class UsuarioService implements UserDetailsService{
	
    @Autowired
    UsuarioDao usuarioDao;
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findUserByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
        return usuario;
    }
}
