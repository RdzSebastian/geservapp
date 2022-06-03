package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.SubTipoEvento;

@SpringBootTest
public class SubTipoEventoTest {
	
	@Test
	void testTipoEventoSave() {
		SubTipoEvento subTipoEvento = new SubTipoEvento();
//		subTipoEvento.setTipoEvento(tipoEventoService.get((long) 1));
		subTipoEvento.setNombre("Teens");
		subTipoEvento.setCantPersonal(10);
//		subTipoEvento.setCapacidad_adultos(10);
//		subTipoEvento.setCapacidad_adultos(10);
		//BBDD
//		subTipoEventoService.save(subTipoEvento);
	}
	
}
