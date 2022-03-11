package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Rol;
import com.estonianport.geservapp.model.Salon;

public interface SalonDao extends CrudRepository<Salon, Long> {

}
