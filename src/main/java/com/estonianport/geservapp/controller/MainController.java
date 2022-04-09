package com.estonianport.geservapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class MainController {

	@Autowired
	SalonService salonService;

	@RequestMapping("/")
	public String index(Model model) {
		List<Salon> listaSalones = salonService.getAll();
		model.addAttribute("listaSalones", listaSalones);
		return "index";
	}
	
	@RequestMapping("/administracion")
	public String adm(Model model, HttpSession session) {
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		return "adm/adm";
	}

}
