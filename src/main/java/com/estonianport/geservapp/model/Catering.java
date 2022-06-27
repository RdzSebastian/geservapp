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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Catering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "catering_tipo_catering",
			joinColumns = @JoinColumn(name = "catering_id"),
			inverseJoinColumns = @JoinColumn(name = "tipo_catering_id"))
	private Set<Extra> listaTipoCatering;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "catering_extra",
			joinColumns = @JoinColumn(name = "catering_id"),
			inverseJoinColumns = @JoinColumn(name = "extra_id"))
	private Set<Extra> listaExtra;
	
	@Column(name = "precio_adultos")
	private int precioAdultos;

	@Column(name = "precio_ninos")
	private int precioNinos;

}
