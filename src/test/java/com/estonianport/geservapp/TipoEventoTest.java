package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.TipoEvento;

@SpringBootTest
public class TipoEventoTest {

	@Test
	void testSalonSave() {
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setNombre("Corto");
		//BBDD
//		tipoEventoService.save(tipoEvento);
	}

}
