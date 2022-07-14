package com.estonianport.geservapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;

public interface EventoExtraVariableSubTipoEventoDao extends CrudRepository<EventoExtraVariableSubTipoEvento, Long> {

	List<EventoExtraVariableSubTipoEvento> getEventoExtraVariableSubTipoEventoByEvento(Evento evento);

}
