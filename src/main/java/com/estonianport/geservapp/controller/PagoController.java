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
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.PagoService;

@Controller
public class PagoController {

	@Autowired
	private PagoService pagoService;
	
	@Autowired
	private EventoService eventoService;

	@RequestMapping("/abmPago")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaPago", pagoService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_PAGO;
	}

	@GetMapping("/savePago/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		
		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);
		
		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.PAGO, pagoService.get(id));
		}else {
			model.addAttribute(GeneralPath.PAGO, new Pago());
		}
		return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PAGO;
	}

	@PostMapping("/savePago")
	public String save(Pago pago, Model model) {
		pagoService.save(pago);
		return GeneralPath.REDIRECT + GeneralPath.ABM_PAGO;
	}

	@GetMapping("/deletePago/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		pagoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_PAGO;
	}
}
