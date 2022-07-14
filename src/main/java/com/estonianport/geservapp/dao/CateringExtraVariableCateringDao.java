package com.estonianport.geservapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Catering;
import com.estonianport.geservapp.model.CateringExtraVariableCatering;

public interface CateringExtraVariableCateringDao extends CrudRepository<CateringExtraVariableCatering, Long> {

	List<CateringExtraVariableCatering> getCateringExtraVariableCateringByCatering(Catering catering);

}
