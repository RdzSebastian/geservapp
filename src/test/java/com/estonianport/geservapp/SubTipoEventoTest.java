package com.estonianport.geservapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;

@SpringBootTest
public class SubTipoEventoTest {

	@Autowired
	private SubTipoEventoService subTipoEventoService;
	
	@Autowired
	private TipoEventoService tipoEventoService;
	
	@Test
	void testTipoEventoSave() {
		SubTipoEvento subTipoEvento = new SubTipoEvento();
		subTipoEvento.setTipoEvento(tipoEventoService.get((long) 1));
		subTipoEvento.setNombre("Teens");
		subTipoEvento.setCantPersonal(10);
		subTipoEvento.setCapacidad_adultos(10);
		subTipoEvento.setCapacidad_adultos(10);
		subTipoEvento.setPrecioBase(12000);
		//BBDD
		subTipoEventoService.save(subTipoEvento);
	}
	
}
