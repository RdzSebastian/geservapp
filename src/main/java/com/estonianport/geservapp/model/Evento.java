package com.estonianport.geservapp.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
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

	@OneToMany(targetEntity = EventoExtra.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "evento_id", referencedColumnName = "id")
	private Set<EventoExtra> EventoExtra;

	@Column
	private LocalDateTime startd;

	@Column
	private LocalDateTime endd;

	@Column
	private int presupuesto;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column
	private String codigo;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public SubTipoEvento getSubTipoEvento() {
		return subTipoEvento;
	}

	public void setSubTipoEvento(SubTipoEvento subTipoEvento) {
		this.subTipoEvento = subTipoEvento;
	}

	public Set<EventoExtra> getEventoExtra() {
		return EventoExtra;
	}

	public void setEventoExtra(Set<EventoExtra> eventoExtra) {
		EventoExtra = eventoExtra;
	}

	public LocalDateTime getStart_date() {
		return startd;
	}

	public void setStart_date(LocalDateTime start_date) {
		this.startd = start_date;
	}

	public LocalDateTime getEnd_date() {
		return endd;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.endd = end_date;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(int presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
