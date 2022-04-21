package com.estonianport.geservapp.service;

import java.util.List;

import com.estonianport.geservapp.commons.GenericService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Pago;

public interface PagoService extends GenericService<Pago, Long>{

	List<Pago> findPagosByEvento(Evento evento);

	Long count();

}
