package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.ExtraVariableSubTipoEventoDao;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.service.ExtraVariableSubTipoEventoService;

@Service
public class ExtraVariableSubTipoEventoServiceImpl extends GenericServiceImpl<ExtraVariableSubTipoEvento, Long> implements ExtraVariableSubTipoEventoService{

	@Autowired
	private ExtraVariableSubTipoEventoDao extraVariableSubTipoEventoDao;

	@Override
	public CrudRepository<ExtraVariableSubTipoEvento, Long> getDao() {
		return extraVariableSubTipoEventoDao;
	}

	public Long count() {
		return extraVariableSubTipoEventoDao.count();
	}

	@Override
	public ExtraVariableSubTipoEvento getExtraVariableSubTipoEventoByNombre(String nombre) {
		return extraVariableSubTipoEventoDao.getExtraVariableSubTipoEventoByNombre(nombre);
	}

}
