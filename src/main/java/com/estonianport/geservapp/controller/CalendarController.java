package com.estonianport.geservapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.estonianport.geservapp.model.Event;
import com.estonianport.geservapp.service.EventService;

@Controller
public class CalendarController {

	@Autowired
	private EventService eventService;
	
	@RequestMapping(path = "/calendar", method = RequestMethod.GET) String index(Model model) {
		return "calendar/index";
	}
	
	@GetMapping("/saveEvent")
	public String save(Event event) {
		eventService.save(event);
		return "redirect:/" + "index";
	}
}
