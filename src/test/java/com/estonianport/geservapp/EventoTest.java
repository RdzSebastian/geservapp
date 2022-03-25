package com.estonianport.geservapp;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;

@SpringBootTest
class EventoTest {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private ExtraService extraService;

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

	@SuppressWarnings("deprecation")
	@Test
	void testEventoConExtras() {
		Evento evento = new Evento();
		evento.setNombre("event test");
		evento.setStart_date(new Date(2022,02,01));
		evento.setEnd_date(new Date(2022,02,02));
		evento.setTipoEvento(tipoEventoService.get((long) 1));
		evento.setSubTipoEvento(subTipoEventoService.get((long) 1));
		evento.setSalon(salonService.get((long) 1));
		
		Set<EventoExtra> eventoExtra = new HashSet<EventoExtra>();

		EventoExtra eventoExtra1 = new EventoExtra();
		eventoExtra1.setExtra(extraService.findExtraByNombre("Animacion"));
		eventoExtra.add(eventoExtra1);

		EventoExtra eventoExtra2 = new EventoExtra();
		eventoExtra2.setExtra(extraService.get((long) 2));
		eventoExtra.add(eventoExtra2);

		evento.setEventoExtra(eventoExtra);

		eventoService.save(evento);
	}

}
