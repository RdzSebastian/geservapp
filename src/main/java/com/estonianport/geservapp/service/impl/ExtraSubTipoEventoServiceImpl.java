package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.ExtraSubTipoEventoDao;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.service.ExtraSubTipoEventoService;

@Service
public class ExtraSubTipoEventoServiceImpl extends GenericServiceImpl<ExtraSubTipoEvento, Long> implements ExtraSubTipoEventoService{

	@Autowired
	private ExtraSubTipoEventoDao extraSubTipoEventoDao;

	@Override
	public CrudRepository<ExtraSubTipoEvento, Long> getDao() {
		return extraSubTipoEventoDao;
	}

	public Long count() {
		return extraSubTipoEventoDao.count();
	}

}
