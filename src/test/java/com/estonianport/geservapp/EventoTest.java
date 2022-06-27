package com.estonianport.geservapp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class EventoTest {

	@Autowired
	private EventoService eventoService;

//	@Autowired
//	private SalonService salonService;
//
//	@Autowired
//	private TipoEventoService tipoEventoService;
//
//	@Autowired
//	private SubTipoEventoService subTipoEventoService;


		@Test
		void test() {
			String jsonMsg = null;
			try {
	
				List<Evento> events = new ArrayList<Evento>();
				Evento event = new Evento();
				event.setNombre("first event");
				event.setStartd(LocalDateTime.parse("20-11-2020 10:30", DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm")));
				events.add(event);
	
				event = new Evento();
				event.setNombre("second event");
				event.setStartd(LocalDateTime.parse("20-11-2020 10:30", DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm")));
				event.setEndd(LocalDateTime.parse("20-11-2020 13:30", DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm")));
				events.add(event);
	
				//FullCalendar pass encoded string 
				ObjectMapper mapper = new ObjectMapper();
				jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
	
				System.out.print(jsonMsg);
			} catch (IOException ioex) {
				System.out.println(ioex.getMessage());
			}
		}

//		@Test
//		void testSalonGetAll() {
//			List<Salon> listaSalones = salonService.getAll();
//			List<Evento> listaEventos = eventoService.getEventosBySalon(listaSalones.get(3));
//			if(!listaEventos.isEmpty()) {
//				for(Evento evento : listaEventos) {
//					System.out.println(evento.getNombre());
//				}
//			}
//		}

	@Test
	void testEventoConExtras() {
		Evento evento = new Evento();
		evento.setNombre("event test");

		evento.setStartd(LocalDateTime.of(2022,02,01,20,30));
		evento.setEndd(LocalDateTime.of(2022,02,01,20,30));
		
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setNombre("Corto");

		SubTipoEvento subTipoEvento = new SubTipoEvento();
		subTipoEvento.setTipoEvento(tipoEvento);
		subTipoEvento.setNombre("Cumpleaños Teens");
		evento.setSubTipoEvento(subTipoEvento);
		
		Salon salon = new Salon();
		salon.setNombre("Salon 1");
		evento.setSalon(salon);

		// Agrega Extras
		Set<Extra> listaExtra = new HashSet<Extra>();

//		Extra extraAnimacion = new Extra();
//		extraAnimacion.setId((long) 1);
//		extraAnimacion.setNombre("Animacion");
//		extraAnimacion.setPrecio(100);
//
//		listaExtra.add(extraAnimacion);
//		
//		Extra extraPersonal = new Extra();
//		extraPersonal.setId((long) 1);
//		extraPersonal.setNombre("Personal");
//		extraPersonal.setPrecio(100);

//		listaExtra.add(extraPersonal);
		
//		evento.setListaExtra(listaExtra);

		System.out.println(eventoService.existsByCodigo("AAAA"));
	}

}
