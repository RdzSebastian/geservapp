package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.Salon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrecioConFechaEnvioContainer {

	private Long id;

	private List<PrecioConFechaContainer> precioConFecha;
	
	private Salon salon;
}
