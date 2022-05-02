package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.ClienteDao;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.service.ClienteService;

@Service
public class ClienteServiceImpl  extends GenericServiceImpl<Cliente, Long> implements ClienteService{

	@Autowired
	private ClienteDao clienteDao;

	@Override
	public CrudRepository<Cliente, Long> getDao() {
		return clienteDao;
	}
	
	public Long count() {
		return clienteDao.count();
	}
}
