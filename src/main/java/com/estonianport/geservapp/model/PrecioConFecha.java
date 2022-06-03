package com.estonianport.geservapp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="precio_con_fecha")
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

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "sub_tipo_evento_precio_con_fecha",
			joinColumns = @JoinColumn(name = "precio_con_fecha_id"),
			inverseJoinColumns = @JoinColumn(name = "sub_tipo_evento_id"))
	private List<SubTipoEvento> listaSubTipoEvento;

}
