package com.estonianport.geservapp.container;

import java.util.List;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;

public class ReservaContainer {

	private Evento evento;

	private List<Extra> extra;

	private String fecha;

	private String inicio;

	private String fin;

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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

}
