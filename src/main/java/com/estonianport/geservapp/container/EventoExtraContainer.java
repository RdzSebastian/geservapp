package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;

public class EventoExtraContainer {

	private Evento evento;

	private List<Extra> extra;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public List<Extra> getExtra() {
		return extra;
	}

	public void setExtra(List<Extra> extra) {
		this.extra = extra;
	}
	
}
