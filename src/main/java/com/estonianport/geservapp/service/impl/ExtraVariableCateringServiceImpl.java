package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.ExtraVariableCateringDao;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.service.ExtraVariableCateringService;

@Service
public class ExtraVariableCateringServiceImpl extends GenericServiceImpl<ExtraVariableCatering, Long> implements ExtraVariableCateringService{

	@Autowired
	private ExtraVariableCateringDao extraCateringDao;

	@Override
	public CrudRepository<ExtraVariableCatering, Long> getDao() {
		return extraCateringDao;
	}

	public Long count() {
		return extraCateringDao.count();
	}

}
