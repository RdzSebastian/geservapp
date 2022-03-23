package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.ExtraDao;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.service.ExtraService;

@Service
public class ExtraServiceImpl extends GenericServiceImpl<Extra, Long> implements ExtraService{

	@Autowired
	private ExtraDao extraDao;

	@Override
	public CrudRepository<Extra, Long> getDao() {
		return extraDao;
	}
	
	public Extra findExtraByNombre(String nombre) {
		return extraDao.findExtraByNombre(nombre);
	}


}
