package com.estonianport.geservapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Eventos;
import com.estonianport.geservapp.service.EventosService;

@Controller
public class CalendarController {

	@Autowired
	private EventosService eventosService;

	@GetMapping("/saveEventos/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		if(id != null && id != 0) {
			model.addAttribute("eventos", eventosService.get(id));
		}else {
			model.addAttribute("eventos", new Eventos());
		}
		return GeneralPath.EVENTOS + GeneralPath.PATH_SEPARATOR + "saveEventos";
	}

	@PostMapping("/saveEventos")
	public String save(Eventos event, Model model) {
		eventosService.save(event);
		return "redirect:/";
	}

	@GetMapping("/deleteEventos/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		eventosService.delete(id);
		return "redirect:/";
	}
}
