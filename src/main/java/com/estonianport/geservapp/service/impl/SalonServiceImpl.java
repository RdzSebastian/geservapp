package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.SalonDao;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.SalonService;

@Service
public class SalonServiceImpl extends GenericServiceImpl<Salon, Long> implements SalonService{

	@Autowired
	private SalonDao salonDao;

	@Override
	public CrudRepository<Salon, Long> getDao() {
		return salonDao;
	}
	
	public Long count() {
		return salonDao.count();
	}

}
