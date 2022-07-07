package com.estonianport.geservapp.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubTipoEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "tipo_evento_id")
	private TipoEvento tipoEvento;

	@ManyToOne
	@JoinColumn(name = "capacidad_id")
	private Capacidad capacidad;

	@Column
	private LocalTime duracion;

	@Column(name = "cant_personal")
	private int cantPersonal;
	
	@Column(name = "valor_fin_semana")
	private int valorFinSemana;

	@Column(name = "horario_final_automatico")
	private boolean horarioFinalAutomatico;

    @OneToMany(mappedBy = "subTipoEvento", cascade = CascadeType.ALL)
    private List<PrecioConFecha> listaPrecioConFecha;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaSubTipoEvento")
	private Set<Servicio> listaServicio;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaSubTipoEvento")
	private Set<ExtraSubTipoEvento> listaExtraSubTipoEvento;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaSubTipoEvento")
	private Set<ExtraVariableSubTipoEvento> listaExtraVariableSubTipoEvento;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaSubTipoEvento")
	private Set<ExtraVariableCatering> listaExtraVariableCatering;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "listaSubTipoEvento")
	private Set<TipoCatering> listaTipoCatering;

}
