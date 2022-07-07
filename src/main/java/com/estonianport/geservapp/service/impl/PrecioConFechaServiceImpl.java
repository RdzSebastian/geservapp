package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaDao;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.service.PrecioConFechaService;

@Service
public class PrecioConFechaServiceImpl  extends GenericServiceImpl<PrecioConFecha, Long> implements PrecioConFechaService{

	@Autowired
	private PrecioConFechaDao precioConFechaDao;

	@Override
	public CrudRepository<PrecioConFecha, Long> getDao() {
		return precioConFechaDao;
	}

}
