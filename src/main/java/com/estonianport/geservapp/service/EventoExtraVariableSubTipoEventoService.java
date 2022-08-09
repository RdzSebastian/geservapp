package com.estonianport.geservapp.service;

import java.util.List;

import com.estonianport.geservapp.commons.genericService.GenericService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;

public interface EventoExtraVariableSubTipoEventoService  extends GenericService<EventoExtraVariableSubTipoEvento, Long>{

	List<EventoExtraVariableSubTipoEvento> getEventoExtraVariableSubTipoEventoByEvento(Evento evento);

}
