package com.estonianport.geservapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.dao.SalonDao;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.SalonService;

@SpringBootTest
public class SalonTest {

	@Autowired
	SalonDao salonDao;
	
	@Autowired
	SalonService salonService;
	
	@Test
	void testSalonSave() {
		Salon salon = new Salon();
		salon.setNombre("Salon");
		
		salonService.save(salon);	
	}
	
	@Test
	void testSalonGetAll() {
		List<Salon> listaSalones = salonService.getAll();
		if(!listaSalones.isEmpty()) {
			for(Salon salon : listaSalones) {
				System.out.println(salon.getNombre());
			}
		}
	}
}
