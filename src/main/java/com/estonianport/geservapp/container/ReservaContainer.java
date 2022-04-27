package com.estonianport.geservapp.container;

import java.util.Set;

import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;

public class ReservaContainer {

	private Evento evento;

	private Cliente cliente;

	private Set<Extra> extra;

	private String fecha;

	private String inicio;

	private String fin;

	private Boolean hastaElOtroDia;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Boolean getHastaElOtroDia() {
		return hastaElOtroDia;
	}

	public void setHastaElOtroDia(Boolean hastaElOtroDia) {
		this.hastaElOtroDia = hastaElOtroDia;
	}

	public Set<Extra> getExtra() {
		return extra;
	}

	public void setExtra(Set<Extra> listaExtra) {
		this.extra = listaExtra;
	}

}
