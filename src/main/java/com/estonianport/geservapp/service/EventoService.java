package com.estonianport.geservapp.service;

import java.time.LocalDateTime;
import java.util.List;

import com.estonianport.geservapp.commons.GenericService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;

public interface EventoService extends GenericService<Evento, Long>{

	List<Evento> getEventosBySalon(Salon salon);

	Evento getEventoByCodigo(String codigo);
	
	List<Evento> findAllByStartdBetweenAndSalon(LocalDateTime start_date,LocalDateTime end_date, Salon salon);

	Boolean existsByCodigo(String codigo);
	
	Long count();

}
