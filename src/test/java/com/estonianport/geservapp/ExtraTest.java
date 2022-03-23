package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.service.ExtraService;

@SpringBootTest
public class ExtraTest {

	@Autowired
	private ExtraService extraService;
	
	@Test
	void testExtraSave() {
		Extra extra1 = new Extra();
		extra1.setNombre("Animacion");
		extra1.setPrecio(200);
		//BBDD
		extraService.save(extra1);

		Extra extra2 = new Extra();
		extra2.setNombre("Capacidad");
		extra2.setPrecio(400);
		//BBDD
		extraService.save(extra2);
	}
	
}
