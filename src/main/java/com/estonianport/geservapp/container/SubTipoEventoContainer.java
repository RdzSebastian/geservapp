package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTipoEventoContainer {

	private SubTipoEvento subTipoEvento;

	private List<PrecioConFechaContainer> precioConFecha;
	
	private Salon salon;
}
