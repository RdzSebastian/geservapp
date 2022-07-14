package com.estonianport.geservapp.service;

import java.util.List;

import com.estonianport.geservapp.commons.GenericService;
import com.estonianport.geservapp.model.Catering;
import com.estonianport.geservapp.model.CateringExtraVariableCatering;

public interface CateringExtraVariableCateringService  extends GenericService<CateringExtraVariableCatering, Long>{

	List<CateringExtraVariableCatering> getCateringExtraVariableCateringByCatering(Catering catering);

}
