package com.estonianport.geservapp;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SalonService;

@SpringBootTest
class EventoTest {
	
	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;

//	@Test
//	void test() {
//		String jsonMsg = null;
//		try {
//
//			List<Evento> events = new ArrayList<Evento>();
//			Evento event = new Evento();
//			event.setNombre("first event");
//			event.setStart_date(new Date(2022,02,01));
//			events.add(event);
//
//			event = new Evento();
//			event.setNombre("second event");
//			event.setStart_date(new Date(2022,02,11));
//			//event.setEnd("2022-02-13");
//			events.add(event);
//
//			//FullCalendar pass encoded string 
//			ObjectMapper mapper = new ObjectMapper();
//			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
//
//		} catch (IOException ioex) {
//			System.out.println(ioex.getMessage());
//		}
//	}
	
	@Test
	void testSalonGetAll() {
		List<Salon> listaSalones = salonService.getAll();
		List<Evento> listaEventos = eventoService.getEventosBySalon(listaSalones.get(3));
		if(!listaEventos.isEmpty()) {
			for(Evento evento : listaEventos) {
				System.out.println(evento.getNombre());
			}
		}
	}

}
