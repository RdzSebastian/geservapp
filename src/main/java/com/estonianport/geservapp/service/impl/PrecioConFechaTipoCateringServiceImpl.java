package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaTipoCateringDao;
import com.estonianport.geservapp.model.PrecioConFechaTipoCatering;
import com.estonianport.geservapp.service.PrecioConFechaTipoCateringService;

@Service
public class PrecioConFechaTipoCateringServiceImpl  extends GenericServiceImpl<PrecioConFechaTipoCatering, Long> implements PrecioConFechaTipoCateringService{

	@Autowired
	private PrecioConFechaTipoCateringDao precioConFechaTipoCateringDao;

	@Override
	public CrudRepository<PrecioConFechaTipoCatering, Long> getDao() {
		return precioConFechaTipoCateringDao;
	}

}
