package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.RolService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.UsuarioService;

@Controller
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@RequestMapping("/abmEvento/{id}")
	public String abm(@PathVariable("id") Long id, Model model, HttpSession session, Authentication authentication) {
		Salon salon = salonService.get(id);
		session.setAttribute("salon", salon);
		model.addAttribute("admin", usuarioService.findUserByUsername(authentication.getName()).getRol() == rolService.get((long)1));
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		eventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO;
	}
}
