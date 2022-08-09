package com.estonianport.geservapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.CateringExtraVariableCateringDao;
import com.estonianport.geservapp.model.Catering;
import com.estonianport.geservapp.model.CateringExtraVariableCatering;
import com.estonianport.geservapp.service.CateringExtraVariableCateringService;

@Service
public class CateringExtraVariableCateringServiceImpl  extends GenericServiceImpl<CateringExtraVariableCatering, Long> implements CateringExtraVariableCateringService{

	@Autowired
	private CateringExtraVariableCateringDao cateringExtraVariableCateringDao;

	@Override
	public CrudRepository<CateringExtraVariableCatering, Long> getDao() {
		return cateringExtraVariableCateringDao;
	}

	@Override
	public List<CateringExtraVariableCatering> getCateringExtraVariableCateringByCatering(Catering catering) {
		return cateringExtraVariableCateringDao.getCateringExtraVariableCateringByCatering(catering);
	}

}
