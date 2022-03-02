package com.estonianport.geservapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estonianport.geservapp.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario,Long>{

	Usuario findUserByUsername(String username);

}
