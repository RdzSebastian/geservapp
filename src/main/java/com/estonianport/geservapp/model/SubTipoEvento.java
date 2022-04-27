package com.estonianport.geservapp.model;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class SubTipoEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "tipo_evento_id")
	private TipoEvento tipoEvento;

	@Column
	private int capacidad;

	@Column
	private LocalTime duracion;

	@Column(name = "cant_personal")
	private int cantPersonal;

	@Column(name = "precio_base")
	private int precioBase;

	@ManyToMany
	@JoinTable(
			name = "sub_tipo_evento_extra",
			joinColumns = @JoinColumn(name = "sub_tipo_evento_id"),
			inverseJoinColumns = @JoinColumn(name = "extra_id"))
	private Set<Extra> listaExtra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getCantPersonal() {
		return cantPersonal;
	}

	public void setCantPersonal(int cantPersonal) {
		this.cantPersonal = cantPersonal;
	}

	public int getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(int precioBase) {
		this.precioBase = precioBase;
	}

}
