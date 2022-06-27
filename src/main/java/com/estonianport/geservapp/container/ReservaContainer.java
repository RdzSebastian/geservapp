package com.estonianport.geservapp.container;

import java.util.Set;

import com.estonianport.geservapp.model.Capacidad;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaContainer {

	private Evento evento;

	private Cliente cliente;

	private Set<ExtraSubTipoEvento> extraSubTipoEvento;

	private String fecha;

	private String inicio;

	private String fin;

	private Boolean hastaElOtroDia;
	
	private Boolean resto24;
	
	private Capacidad capacidad;

}
