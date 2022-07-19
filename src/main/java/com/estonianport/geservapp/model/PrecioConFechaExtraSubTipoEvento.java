package com.estonianport.geservapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name="precio_con_fecha_extra_sub_tipo_evento")
@Getter
@Setter
public class PrecioConFechaExtraSubTipoEvento extends PrecioConFecha {

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "extra_sub_tipo_evento_id")
	private ExtraSubTipoEvento extraSubTipoEvento;

}
