package com.estonianport.geservapp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class PrecioConFecha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int precio;

	@Column
	private LocalDateTime desde;

	@Column
	private LocalDateTime hasta;

	@ManyToOne
	@JoinColumn(name = "salon_id")
	private Salon salon;

}
