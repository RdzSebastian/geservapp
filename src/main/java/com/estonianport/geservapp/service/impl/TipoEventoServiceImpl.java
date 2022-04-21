package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.TipoEventoDao;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.TipoEventoService;

@Service
public class TipoEventoServiceImpl extends GenericServiceImpl<TipoEvento, Long> implements TipoEventoService{

	@Autowired
	private TipoEventoDao tipoEvento;

	@Override
	public CrudRepository<TipoEvento, Long> getDao() {
		return tipoEvento;
	}
	
	public Long count() {
		return tipoEvento.count();
	}

}
