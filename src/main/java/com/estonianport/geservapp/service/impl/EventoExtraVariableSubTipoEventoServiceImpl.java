package com.estonianport.geservapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.genericService.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventoExtraVariableSubTipoEventoDao;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;
import com.estonianport.geservapp.service.EventoExtraVariableSubTipoEventoService;

@Service
public class EventoExtraVariableSubTipoEventoServiceImpl extends GenericServiceImpl<EventoExtraVariableSubTipoEvento, Long> implements EventoExtraVariableSubTipoEventoService{

	@Autowired
	private EventoExtraVariableSubTipoEventoDao eventoExtraVariableSubTipoEventoDao;

	@Override
	public CrudRepository<EventoExtraVariableSubTipoEvento, Long> getDao() {
		return eventoExtraVariableSubTipoEventoDao;
	}

	@Override
	public List<EventoExtraVariableSubTipoEvento> getEventoExtraVariableSubTipoEventoByEvento(Evento evento) {
		return eventoExtraVariableSubTipoEventoDao.getEventoExtraVariableSubTipoEventoByEvento(evento);
	}

}
