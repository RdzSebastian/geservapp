package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.CateringDao;
import com.estonianport.geservapp.model.Catering;
import com.estonianport.geservapp.service.CateringService;

@Service
public class CateringServiceImpl  extends GenericServiceImpl<Catering, Long> implements CateringService{

	@Autowired
	private CateringDao cateringDao;

	@Override
	public CrudRepository<Catering, Long> getDao() {
		return cateringDao;
	}

}
