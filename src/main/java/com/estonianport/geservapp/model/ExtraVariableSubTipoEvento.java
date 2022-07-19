package com.estonianport.geservapp.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExtraVariableSubTipoEvento extends Extra {

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "sub_tipo_evento_extra_variable",
			joinColumns = @JoinColumn(name = "extra_variable_id"),
			inverseJoinColumns = @JoinColumn(name = "sub_tipo_evento_id"))
	private Set<SubTipoEvento> listaSubTipoEvento;

	@JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "extraVariableSubTipoEvento")
    private List<PrecioConFechaExtraVariableSubTipoEvento> listaPrecioConFecha;

}
