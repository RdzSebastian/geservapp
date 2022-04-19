package com.estonianport.geservapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Pago;

public interface PagoDao extends CrudRepository<Pago, Long> {

	List<Pago> findPagosByEvento(Evento evento);

}
