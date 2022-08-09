package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaExtraSubTipoEventoDao;
import com.estonianport.geservapp.model.PrecioConFechaExtraSubTipoEvento;
import com.estonianport.geservapp.service.PrecioConFechaExtraSubTipoEventoService;

@Service
public class PrecioConFechaExtraSubTipoEventoServiceImpl  extends GenericServiceImpl<PrecioConFechaExtraSubTipoEvento, Long> implements PrecioConFechaExtraSubTipoEventoService{

	@Autowired
	private PrecioConFechaExtraSubTipoEventoDao precioConFechaExtraSubTipoEventoDao;

	@Override
	public CrudRepository<PrecioConFechaExtraSubTipoEvento, Long> getDao() {
		return precioConFechaExtraSubTipoEventoDao;
	}

}
