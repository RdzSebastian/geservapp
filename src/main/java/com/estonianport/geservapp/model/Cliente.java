package com.estonianport.geservapp.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private String apellido;

	@ManyToOne
	@JoinColumn(name = "sexo_id")
	private Sexo sexo;

	@Column
	private long cuil;

	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Column
	private String empresa;

	@Column
	private String ciudad;

	@Column
	private String provincia;

	@Column
	private int codigoPostal;

	@Column
	private String email;

	@Column
	private long celular;
	
	@JsonBackReference
	@OneToMany(mappedBy = "cliente")
	private Set<Evento> evento;

}
