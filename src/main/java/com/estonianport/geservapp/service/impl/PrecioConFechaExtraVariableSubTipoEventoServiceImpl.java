package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.PrecioConFechaExtraVariableSubTipoEventoDao;
import com.estonianport.geservapp.model.PrecioConFechaExtraVariableSubTipoEvento;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableSubTipoEventoService;

@Service
public class PrecioConFechaExtraVariableSubTipoEventoServiceImpl  extends GenericServiceImpl<PrecioConFechaExtraVariableSubTipoEvento, Long> implements PrecioConFechaExtraVariableSubTipoEventoService{

	@Autowired
	private PrecioConFechaExtraVariableSubTipoEventoDao precioConFechaExtraVariableSubTipoEventoDao;

	@Override
	public CrudRepository<PrecioConFechaExtraVariableSubTipoEvento, Long> getDao() {
		return precioConFechaExtraVariableSubTipoEventoDao;
	}

}
