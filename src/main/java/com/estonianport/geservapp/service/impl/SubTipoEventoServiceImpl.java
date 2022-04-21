package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.SubTipoEventoDao;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Service
public class SubTipoEventoServiceImpl extends GenericServiceImpl<SubTipoEvento, Long> implements SubTipoEventoService{

	@Autowired
	private SubTipoEventoDao subTipoEvento;

	@Override
	public CrudRepository<SubTipoEvento, Long> getDao() {
		return subTipoEvento;
	}
	
	public Long count() {
		return subTipoEvento.count();
	}


}
