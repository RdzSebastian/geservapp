package com.estonianport.geservapp.model;

import java.time.LocalDateTime;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_tipo_evento_id")
	private SubTipoEvento subTipoEvento;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "evento_extra_sub_tipo_evento",
			joinColumns = @JoinColumn(name = "evento_id"),
			inverseJoinColumns = @JoinColumn(name = "extra_sub_tipo_evento_id"))
	private Set<ExtraSubTipoEvento> listaExtraSubTipoEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private Set<EventoExtraVariableSubTipoEvento> eventoExtraVariable;
    
	@Column
	private LocalDateTime startd;

	@Column
	private LocalDateTime endd;

	@ManyToOne
	@JoinColumn(name = "capacidad_id")
	private Capacidad capacidad;
	
	@ManyToOne
	@JoinColumn(name = "catering_id")
	private Catering catering;

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
	
	@Column
	private int extraOtro;
	
	@Column
	private int descuento;


}
