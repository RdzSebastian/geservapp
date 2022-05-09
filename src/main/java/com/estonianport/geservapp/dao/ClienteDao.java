package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Long> {

	boolean existsByCuil(long cuil);

	Cliente getClienteByCuil(long cuil);

}
