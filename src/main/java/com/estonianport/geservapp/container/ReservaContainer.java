package com.estonianport.geservapp.container;

import java.util.Set;

import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.ExtraCatering;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.TipoCatering;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaContainer {

	private Evento evento;

	private Cliente cliente;

	private Set<ExtraSubTipoEvento> extraSubTipoEvento;
	
	private Set<ExtraVariableSubTipoEvento> extraVariableSubTipoEvento;

	private Set<ExtraCatering> extraCatering;
	
	private Set<TipoCatering> tipoCatering;

	private String fecha;

	private String inicio;

	private String fin;

	private Boolean hastaElOtroDia;
	
	private Boolean resto24;

}
