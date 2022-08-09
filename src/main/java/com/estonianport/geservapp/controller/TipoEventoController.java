package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estonianport.geservapp.commons.data.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.TipoEventoService;

@Controller
public class TipoEventoController {

	@Autowired
	private TipoEventoService tipoEventoService;

	@RequestMapping("/abmTipoEvento")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaTipoEvento", tipoEventoService.getAll());
		
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_TIPO_EVENTO;
	}

	@GetMapping("/saveTipoEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.TIPO_EVENTO, tipoEventoService.get(id));
		}else {
			model.addAttribute(GeneralPath.TIPO_EVENTO, new TipoEvento());
		}
		return GeneralPath.TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_TIPO_EVENTO;
	}

	@PostMapping("/saveTipoEvento")
	public String save(TipoEvento tipoEvento, Model model) {
		tipoEventoService.save(tipoEvento);
		return GeneralPath.REDIRECT + GeneralPath.ABM_TIPO_EVENTO;
	}

	@GetMapping("/deleteTipoEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		tipoEventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_TIPO_EVENTO;
	}
}
