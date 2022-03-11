package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Evento;

public interface EventoDao extends CrudRepository<Evento, Long> {
	
}
