package com.estonianport.geservapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estonianport.geservapp.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/event")
public class RestWebController {

	/**
	 * acquires Event information to be displayed on the calendar
	 * @return Event information json encoded string
	 */

	@GetMapping(value = "/all")
	public String getEvents() {
		String jsonMsg = null;
		try {

			List<Event> events = new ArrayList<Event>();
			Event event = new Event();
			event.setTitle("first event");
			event.setStart("2022-02-01");
			events.add(event);

			event = new Event();
			event.setTitle("second event");
			event.setStart("2022-02-11");
			//event.setEnd("2022-02-13");
			events.add(event);

			//FullCalendar pass encoded string 
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
	}
}
