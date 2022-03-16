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
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.TipoEventoService;

@Controller
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;
	
	@Autowired
	private TipoEventoService tipoEventoService;

	@RequestMapping("/abmEvento/{id}")
	public String abm(@PathVariable("id") Long id, Model model, HttpSession session) {
		Salon salon = salonService.get(id);
		session.setAttribute("salon", salon);
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, HttpSession session) {
		List<TipoEvento> listaTipoEvento = tipoEventoService.getAll();
		model.addAttribute("listaTipoEvento", listaTipoEvento);
		
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.EVENTO, eventoService.get(id));
		}else {
			model.addAttribute(GeneralPath.EVENTO, new Evento());
		}
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
	}

	@PostMapping("/saveEvento")
	public String save(Evento evento, Model model, HttpSession session) {
		
		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		
		evento.setSalon(salon);
		eventoService.save(evento);
		
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		eventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO;
	}
}
