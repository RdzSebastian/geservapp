package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventoDao;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.service.EventoService;

@Service
public class EventoServiceImpl extends GenericServiceImpl<Evento, Long> implements EventoService{

	@Autowired
	private EventoDao eventosDao;

	@Override
	public CrudRepository<Evento, Long> getDao() {
		return eventosDao;
	}
}
