package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.SexoDao;
import com.estonianport.geservapp.model.Sexo;
import com.estonianport.geservapp.service.SexoService;

@Service
public class SexoServiceImpl extends GenericServiceImpl<Sexo, Long> implements SexoService{

	@Autowired
	private SexoDao sexoDao;

	@Override
	public CrudRepository<Sexo, Long> getDao() {
		return sexoDao;
	}
}
