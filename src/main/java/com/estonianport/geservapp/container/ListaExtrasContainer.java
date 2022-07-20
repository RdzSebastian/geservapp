package com.estonianport.geservapp.container;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaExtrasContainer {

	private List<ExtraContainer> listaExtra;
	
	private List<ExtraContainer> listaExtraVariable;

	private List<ExtraContainer> listaExtraCatering;

	private List<ExtraContainer> listaTipoCatering;

}
