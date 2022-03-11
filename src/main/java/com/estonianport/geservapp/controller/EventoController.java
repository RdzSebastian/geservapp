package com.estonianport.geservapp.controller;

import java.util.List;

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
public class EventoController {

	@Autowired
	private EventoService eventosService;
	
	@Autowired
	private SalonService salonService;
	
	@RequestMapping("/abmEvento")
	public String abm(Model model) {
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		List<Salon> listaSalones = salonService.getAll();
		model.addAttribute("listaSalones", listaSalones);
		if(id != null && id != 0) {
			model.addAttribute("eventos", eventosService.get(id));
		}else {
			model.addAttribute("eventos", new Evento());
		}
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
	}

	@PostMapping("/saveEvento")
	public String save(Evento event, Model model) {
		eventosService.save(event);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		eventosService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO;
	}
}
