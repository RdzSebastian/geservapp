package com.estonianport.geservapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name="precio_con_fecha_sub_tipo_evento")
@Getter
@Setter
public class PrecioConFechaSubTipoEvento extends PrecioConFecha {

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_tipo_evento_id")
	private SubTipoEvento subTipoEvento;

}
