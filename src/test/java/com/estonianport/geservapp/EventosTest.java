package com.estonianport.geservapp;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.model.Eventos;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class EventosTest {

	@Test
	void test() {
		String jsonMsg = null;
		try {

			List<Eventos> events = new ArrayList<Eventos>();
			Eventos event = new Eventos();
			event.setNombre("first event");
			event.setStart_date(new Date(2022,02,01));
			events.add(event);

			event = new Eventos();
			event.setNombre("second event");
			event.setStart_date(new Date(2022,02,11));
			//event.setEnd("2022-02-13");
			events.add(event);

			//FullCalendar pass encoded string 
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
	}

}
