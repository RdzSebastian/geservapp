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
import com.estonianport.geservapp.model.Servicio;
import com.estonianport.geservapp.service.ServicioService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class ServicioController {

	@Autowired
	private ServicioService servicioService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@RequestMapping("/abmServicio")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaServicio", servicioService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.SERVICIO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_SERVICIO;
	}

	@GetMapping("/saveServicio/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			Servicio servicio = servicioService.get(id);
			model.addAttribute("listaSubTipoEventoSeleccionadas", servicio.getListaSubTipoEvento());
			model.addAttribute(GeneralPath.SERVICIO, servicio);
		}else {
			model.addAttribute(GeneralPath.SERVICIO, new Servicio());
		}
		return GeneralPath.SERVICIO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_SERVICIO;
	}

	@PostMapping("/saveServicio")
	public String save(Servicio servicio, Model model) {
		servicioService.save(servicio);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SERVICIO;
	}

	@GetMapping("/deleteServicio/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {

		Servicio servicio = servicioService.get(id);

		// Elimina los subTipoEvento Vinculados
		servicio.setListaSubTipoEvento(null);
		servicioService.save(servicio);

		servicioService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SERVICIO;
	}
}
