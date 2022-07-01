package com.estonianport.geservapp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Catering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "precio_adultos")
	private int precioAdultos;

	@Column(name = "precio_ninos")
	private int precioNinos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "catering_tipo_catering",
			joinColumns = @JoinColumn(name = "catering_id"),
			inverseJoinColumns = @JoinColumn(name = "tipo_catering_id"))
	private Set<TipoCatering> listaTipoCatering;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaCatering")
	private Set<ExtraCatering> listaExtraCatering;

}
