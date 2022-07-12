package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;

public interface ExtraVariableSubTipoEventoDao extends CrudRepository<ExtraVariableSubTipoEvento, Long> {

	ExtraVariableSubTipoEvento getExtraVariableSubTipoEventoByNombre(String nombre);
	
}
