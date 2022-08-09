package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.TipoCateringDao;
import com.estonianport.geservapp.model.TipoCatering;
import com.estonianport.geservapp.service.TipoCateringService;

@Service
public class TipoCateringServiceImpl extends GenericServiceImpl<TipoCatering, Long> implements TipoCateringService{

	@Autowired
	private TipoCateringDao tipoCateringDao;

	@Override
	public CrudRepository<TipoCatering, Long> getDao() {
		return tipoCateringDao;
	}

	public Long count() {
		return tipoCateringDao.count();
	}

}
