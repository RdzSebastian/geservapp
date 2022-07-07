package com.estonianport.geservapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CateringExtraVariableCatering {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catering_id")
	private Catering catering;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "extra_variable_catering_id")
	private ExtraVariableCatering extraVariableCatering;

	@Column
	private int cantidad;

}
