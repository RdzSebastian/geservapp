package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.RolDao;
import com.estonianport.geservapp.model.Rol;
import com.estonianport.geservapp.service.RolService;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, Long> implements RolService{

	@Autowired
	private RolDao rolDao;

	@Override
	public CrudRepository<Rol, Long> getDao() {
		return rolDao;
	}
}