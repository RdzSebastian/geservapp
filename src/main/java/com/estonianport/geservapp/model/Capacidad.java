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
public class Capacidad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "capacidad_variable")
	private Boolean capacidadVariable;

	@Column(name = "capacidad_adultos")
	private int capacidadAdultos;

	@Column(name = "capacidad_ninos")
	private int capacidadNinos;

}
