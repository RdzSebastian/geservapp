package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaExtraVariableCateringDao;
import com.estonianport.geservapp.model.PrecioConFechaExtraVariableCatering;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableCateringService;

@Service
public class PrecioConFechaExtraVariableCateringServiceImpl  extends GenericServiceImpl<PrecioConFechaExtraVariableCatering, Long> implements PrecioConFechaExtraVariableCateringService{

	@Autowired
	private PrecioConFechaExtraVariableCateringDao precioConFechaExtraVariableCateringDao;

	@Override
	public CrudRepository<PrecioConFechaExtraVariableCatering, Long> getDao() {
		return precioConFechaExtraVariableCateringDao;
	}

}
