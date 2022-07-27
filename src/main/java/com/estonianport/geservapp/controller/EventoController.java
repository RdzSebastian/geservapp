package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class EventoController {

	@Autowired
	private SalonService salonService;

	@RequestMapping("/abmEvento/{id}")
	public String abm(@PathVariable("id") Long id, Model model, HttpSession session) {
		Salon salon = salonService.get(id);
		session.setAttribute("salon", salon);

		// Setea el id de salon y volver abmEvento por si selecciona una fecha
		session.setAttribute(GeneralPath.VOLVER, GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + id);

		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

}
