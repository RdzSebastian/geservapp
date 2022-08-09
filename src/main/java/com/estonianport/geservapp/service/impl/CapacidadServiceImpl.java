package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.CapacidadDao;
import com.estonianport.geservapp.model.Capacidad;
import com.estonianport.geservapp.service.CapacidadService;

@Service
public class CapacidadServiceImpl  extends GenericServiceImpl<Capacidad, Long> implements CapacidadService{

	@Autowired
	private CapacidadDao capacidadDao;

	@Override
	public CrudRepository<Capacidad, Long> getDao() {
		return capacidadDao;
	}

}
