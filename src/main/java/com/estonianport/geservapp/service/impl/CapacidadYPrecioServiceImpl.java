package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.CapacidadYPrecioDao;
import com.estonianport.geservapp.model.CapacidadYPrecio;
import com.estonianport.geservapp.service.CapacidadYPrecioService;

@Service
public class CapacidadYPrecioServiceImpl  extends GenericServiceImpl<CapacidadYPrecio, Long> implements CapacidadYPrecioService{

	@Autowired
	private CapacidadYPrecioDao capacidadYPrecioDao;

	@Override
	public CrudRepository<CapacidadYPrecio, Long> getDao() {
		return capacidadYPrecioDao;
	}

}
