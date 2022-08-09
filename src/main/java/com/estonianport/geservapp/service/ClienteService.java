package com.estonianport.geservapp.service;

import com.estonianport.geservapp.commons.genericService.GenericService;
import com.estonianport.geservapp.model.Cliente;

public interface ClienteService  extends GenericService<Cliente, Long>{

	Long count();

	boolean existsByCuil(long cuil);

	Cliente getClienteByCuil(long cuil);

}
