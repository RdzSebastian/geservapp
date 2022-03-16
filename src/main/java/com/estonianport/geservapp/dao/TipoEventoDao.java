package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.TipoEvento;

public interface TipoEventoDao extends CrudRepository<TipoEvento, Long> {

}
