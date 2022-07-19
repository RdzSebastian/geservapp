package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.Salon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraSubTipoEventoContainer {

	private ExtraSubTipoEvento extraSubTipoEvento;

	private List<PrecioConFechaContainer> precioConFecha;
	
	private Salon salon;
}
