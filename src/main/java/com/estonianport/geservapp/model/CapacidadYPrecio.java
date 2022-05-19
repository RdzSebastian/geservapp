package com.estonianport.geservapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CapacidadYPrecio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "capacidad_adultos")
	private int capacidadAdultos;

	@Column(name = "capacidad_ninos")
	private int capacidadNinos;

	@Column(name = "precio_adultos")
	private int precioAdultos;

	@Column(name = "precio_ninos")
	private int precioNinos;

}
