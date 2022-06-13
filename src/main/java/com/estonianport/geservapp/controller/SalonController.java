package com.estonianport.geservapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class SalonController {

	@Autowired
	private SalonService salonService;
	
	@Autowired
	private EventoService eventoService;

	@RequestMapping("/abmSalon")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaSalon", salonService.getAll());
		
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.SALON + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_SALON;
	}

	@GetMapping("/saveSalon/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.SALON, salonService.get(id));
		}else {
			model.addAttribute(GeneralPath.SALON, new Salon());
		}
		return GeneralPath.SALON + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_SALON;
	}

	@PostMapping("/saveSalon")
	public String save(Salon salon, Model model) {
		salonService.save(salon);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SALON;
	}

	@GetMapping("/deleteSalon/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		
		//Elimina todos los eventos del salon 
		List<Evento> listaEventos = eventoService.getEventosBySalon(salonService.get(id));
		for(Evento evento : listaEventos) {
			eventoService.delete(evento.getId());
		}
		
		//Elmina el salon
		salonService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SALON;
	}
}
