package com.estonianport.geservapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;

public interface EventoDao extends CrudRepository<Evento, Long> {
	
	List<Evento> getEventosBySalon(Salon salon);
	
}
