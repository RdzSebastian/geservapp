package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.TipoCatering;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoCateringService;

@Controller
public class TipoCateringController {

	@Autowired
	private TipoCateringService tipoCateringService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@RequestMapping("/abmTipoCatering")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaTipoCatering", tipoCateringService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.TIPO_CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_TIPO_CATERING;
	}

	@GetMapping("/saveTipoCatering/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			TipoCatering tipoCatering = tipoCateringService.get(id);
			model.addAttribute("listaSubTipoEventoSeleccionadas", tipoCatering.getListaSubTipoEvento());
			model.addAttribute(GeneralPath.TIPO_CATERING, tipoCatering);
		}else {
			model.addAttribute(GeneralPath.TIPO_CATERING, new TipoCatering());
		}
		return GeneralPath.CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.TIPO_CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_TIPO_CATERING;
	}

	@PostMapping("/saveTipoCatering")
	public String save(TipoCatering tipoCatering, Model model) {
		tipoCateringService.save(tipoCatering);
		return GeneralPath.REDIRECT + GeneralPath.ABM_TIPO_CATERING;
	}

	@GetMapping("/deleteTipoCatering/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		tipoCateringService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_TIPO_CATERING;
	}
}
