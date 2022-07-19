package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.Salon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraVariableCateringContainer {

	private ExtraVariableCatering extraVariableCatering;

	private List<PrecioConFechaContainer> precioConFecha;
	
	private Salon salon;
}
