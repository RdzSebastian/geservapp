package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;

	@RequestMapping("/abmEvento/{id}")
	public String abm(@PathVariable("id") Long id, Model model, HttpSession session) {
		Salon salon = salonService.get(id);
		session.setAttribute("salon", salon);
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		eventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO;
	}
}
