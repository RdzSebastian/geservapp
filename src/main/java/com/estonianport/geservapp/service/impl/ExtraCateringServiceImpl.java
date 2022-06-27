package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.ExtraCateringDao;
import com.estonianport.geservapp.model.ExtraCatering;
import com.estonianport.geservapp.service.ExtraCateringService;

@Service
public class ExtraCateringServiceImpl extends GenericServiceImpl<ExtraCatering, Long> implements ExtraCateringService{

	@Autowired
	private ExtraCateringDao extraCateringDao;

	@Override
	public CrudRepository<ExtraCatering, Long> getDao() {
		return extraCateringDao;
	}

	public Long count() {
		return extraCateringDao.count();
	}

}
