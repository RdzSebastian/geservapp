package com.estonianport.geservapp.container;

import java.util.List;
import java.util.Set;

import com.estonianport.geservapp.model.CateringExtraVariableCatering;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableCatering;
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

	private Set<ExtraVariableCatering> extraCatering;
	
	private Set<TipoCatering> tipoCatering;
	
	private List<EventoExtraVariableSubTipoEvento> eventoExtraVariableSubTipoEvento;
	
	private List<CateringExtraVariableCatering> cateringExtraVariableCatering;

	private String fecha;

	private String inicio;

	private String fin;

	private Boolean hastaElOtroDia;
	
	private Boolean resto24;

}
