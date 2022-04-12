package com.estonianport.geservapp.service;

import com.estonianport.geservapp.commons.GenericService;
import com.estonianport.geservapp.model.Usuario;

public interface UsuarioService extends GenericService<Usuario, Long>{

	public Usuario findUserByUsername(String username);
}
