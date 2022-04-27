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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Set<Extra> getListaExtra() {
		return listaExtra;
	}

	public void setListaExtra(Set<Extra> listaExtra) {
		this.listaExtra = listaExtra;
	}

}
