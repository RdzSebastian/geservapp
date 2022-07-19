package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Salon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraVariableSubTipoEventoContainer {

	private ExtraVariableSubTipoEvento extraVariableSubTipoEvento;

	private List<PrecioConFechaContainer> precioConFecha;
	
	private Salon salon;
}
