package com.estonianport.geservapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventoDao;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;

@Service
public class EventoServiceImpl extends GenericServiceImpl<Evento, Long> implements EventoService{

	@Autowired
	private EventoDao eventosDao;

	@Override
	public CrudRepository<Evento, Long> getDao() {
		return eventosDao;
	}

	public List<Evento> getEventosBySalon(Salon salon){
		return eventosDao.getEventosBySalon(salon);
	}

	@Override
	public Evento getEventoByCodigo(String codigo) {
		return eventosDao.getEventoByCodigo(codigo);
	}

	@Override
	public List<Evento> findAllByStartdBetweenAndSalon(LocalDateTime start_date,LocalDateTime end_date, Salon salon){
		return eventosDao.findAllByStartdBetweenAndSalon(start_date, end_date, salon);
	}

	public Long count() {
		return eventosDao.count();
	}

	@Override
	public Boolean existsByCodigo(String codigo) {
		return eventosDao.existsByCodigo(codigo);
	}

	@Override
	public boolean existByFechaAndSalon(LocalDateTime startd, Salon salon) {
		return false; //eventosDao.existByStartd(Timestamp.valueOf(startd));
	}

}
