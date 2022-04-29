package com.estonianport.geservapp.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;

public interface EventoDao extends CrudRepository<Evento, Long> {
	
	List<Evento> getEventosBySalon(Salon salon);

	Evento getEventoByCodigo(String codigo);

	List<Evento> findAllByStartdBetweenAndSalon(LocalDateTime start_date, LocalDateTime end_date, Salon salon);

	Boolean existsByCodigo(String codigo);
	
}
