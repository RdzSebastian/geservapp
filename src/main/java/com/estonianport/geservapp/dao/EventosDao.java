package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Eventos;

public interface EventosDao extends CrudRepository<Eventos, Long> {
	
}
