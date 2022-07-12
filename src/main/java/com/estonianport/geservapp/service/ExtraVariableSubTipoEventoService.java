package com.estonianport.geservapp.service;

import com.estonianport.geservapp.commons.GenericService;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;

public interface ExtraVariableSubTipoEventoService extends GenericService<ExtraVariableSubTipoEvento, Long>{

	Long count();

	ExtraVariableSubTipoEvento getExtraVariableSubTipoEventoByNombre(String nombre);

}
