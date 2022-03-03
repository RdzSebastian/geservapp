package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Rol;

public interface RolDao extends CrudRepository<Rol, Long> {
	
}
