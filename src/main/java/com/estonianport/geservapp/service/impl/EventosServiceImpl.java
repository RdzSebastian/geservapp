package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventosDao;
import com.estonianport.geservapp.model.Eventos;
import com.estonianport.geservapp.service.EventosService;

@Service
public class EventosServiceImpl extends GenericServiceImpl<Eventos, Long> implements EventosService{

	@Autowired
	private EventosDao eventosDao;

	@Override
	public CrudRepository<Eventos, Long> getDao() {
		return eventosDao;
	}
}
