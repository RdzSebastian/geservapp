package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaSubTipoEventoDao;
import com.estonianport.geservapp.model.PrecioConFechaSubTipoEvento;
import com.estonianport.geservapp.service.PrecioConFechaSubTipoEventoService;

@Service
public class PrecioConFechaSubTipoEventoServiceImpl  extends GenericServiceImpl<PrecioConFechaSubTipoEvento, Long> implements PrecioConFechaSubTipoEventoService{

	@Autowired
	private PrecioConFechaSubTipoEventoDao precioConFechaSubTipoEventoFechaDao;

	@Override
	public CrudRepository<PrecioConFechaSubTipoEvento, Long> getDao() {
		return precioConFechaSubTipoEventoFechaDao;
	}

}
