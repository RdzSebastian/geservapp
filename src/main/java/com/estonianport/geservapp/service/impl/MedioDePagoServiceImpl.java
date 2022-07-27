package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.MedioDePagoDao;
import com.estonianport.geservapp.model.MedioDePago;
import com.estonianport.geservapp.service.MedioDePagoService;

@Service
public class MedioDePagoServiceImpl extends GenericServiceImpl<MedioDePago, Long> implements MedioDePagoService{

	@Autowired
	private MedioDePagoDao medioDePagoDao;

	@Override
	public CrudRepository<MedioDePago, Long> getDao() {
		return medioDePagoDao;
	}
	
	public Long count() {
		return medioDePagoDao.count();
	}

}
