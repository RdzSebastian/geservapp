package com.estonianport.geservapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.FullCalendarJSON;
import com.estonianport.geservapp.service.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/eventos")
public class RestWebController {

	@Autowired
	private EventoService eventosService;

	/**
	 * acquires Event information to be displayed on the calendar
	 * @return Event information json encoded string
	 */

	@GetMapping(value = "/all")
	public String getEventos() {
		String jsonMsg = null;
		try {

			List<FullCalendarJSON> eventos = new ArrayList<FullCalendarJSON>();
			FullCalendarJSON evento = null;

			List<Evento> eventosDDBB = eventosService.getAll();

			for (Evento eventoDDBB : eventosDDBB) {
				evento = new FullCalendarJSON();

				evento.setId(eventoDDBB.getId());
				evento.setTitle(eventoDDBB.getNombre());

				if(eventoDDBB.getStart_date() != null) {
					evento.setStart(eventoDDBB.getStart_date().toString());
				}

				if(eventoDDBB.getEnd_date() != null) {
					evento.setEnd(eventoDDBB.getEnd_date().toString());	
				}
				eventos.add(evento);
			}

			//FullCalendar pass encoded string 
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventos);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
	}

}
