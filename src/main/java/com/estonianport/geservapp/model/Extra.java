package com.estonianport.geservapp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Extra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private int precio;

	@ManyToMany(mappedBy = "listaExtra")
	private Set<Evento> evento;

	@ManyToMany
	@JoinTable(
			name = "sub_tipo_evento_extra",
			joinColumns = @JoinColumn(name = "extra_id"),
			inverseJoinColumns = @JoinColumn(name = "sub_tipo_evento_id"))
	private Set<SubTipoEvento> listaSubTipoEvento;

}
