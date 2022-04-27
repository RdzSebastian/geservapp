package com.estonianport.geservapp.model;

import java.time.LocalDateTime;
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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "salon_id")
	private Salon salon;

	@ManyToOne
	@JoinColumn(name = "tipo_evento_id")
	private TipoEvento tipoEvento;

	@ManyToOne
	@JoinColumn(name = "sub_tipo_evento_id")
	private SubTipoEvento subTipoEvento;

	@ManyToMany
	@JoinTable(
			name = "evento_extra",
			joinColumns = @JoinColumn(name = "evento_id"),
			inverseJoinColumns = @JoinColumn(name = "extra_id"))
	private Set<Extra> listaExtra;

	@Column
	private LocalDateTime startd;

	@Column
	private LocalDateTime endd;

	@Column
	private int presupuesto;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Column
	private String codigo;

}
