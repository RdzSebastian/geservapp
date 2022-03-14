package com.estonianport.geservapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
