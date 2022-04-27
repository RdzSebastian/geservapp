package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.TipoEventoService;

@SpringBootTest
public class TipoEventoTest {

	@Autowired
	private TipoEventoService tipoEventoService;

	@Test
	void testSalonSave() {
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setNombre("Corto");
		//BBDD
		tipoEventoService.save(tipoEvento);
	}

}
