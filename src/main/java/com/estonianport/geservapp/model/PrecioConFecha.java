package com.estonianport.geservapp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name="precio_con_fecha")
@Getter
@Setter
public class PrecioConFecha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int precio;

	@Column
	private LocalDateTime desde;

	@Column
	private LocalDateTime hasta;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaPrecioConFecha")
	private List<SubTipoEvento> listaSubTipoEvento;
}
