package com.estonianport.geservapp.service;

import com.estonianport.geservapp.commons.genericService.GenericService;
import com.estonianport.geservapp.model.Rol;

public interface RolService extends GenericService<Rol, Long>{
	
	Rol getRolByNombre(String nombre);

}
