package com.estonianport.geservapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.PagoDao;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.service.PagoService;

@Service
public class PagoServiceImpl extends GenericServiceImpl<Pago, Long> implements PagoService{

	@Autowired
	private PagoDao pagoDao;

	@Override
	public CrudRepository<Pago, Long> getDao() {
		return pagoDao;
	}

	@Override
	public List<Pago> findPagosByEvento(Evento evento) {
		return pagoDao.findPagosByEvento(evento);
	}

	public Long count() {
		return pagoDao.count();
	}
}
