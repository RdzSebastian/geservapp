package com.estonianport.geservapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.PagoService;
import com.estonianport.geservapp.service.UsuarioService;

@Controller
public class PagoController {

	@Autowired
	private PagoService pagoService;
	
	@Autowired
	private EventoService eventoService;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/abmPago")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaPago", pagoService.getAll());
		session.setAttribute("volver", "/savePago/0");

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_PAGO;
	}

	@PostMapping("/savePago/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, CodigoContainer codigoContainer) {
		
		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);

		if(codigoContainer.getCodigo() != null) {
			Pago pago = new Pago();
			pago.setEvento(eventoService.getEventoByCodigo(codigoContainer.getCodigo()));
			model.addAttribute(GeneralPath.PAGO, pago);
		}

		return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PAGO;
	}

	@PostMapping("/savePago")
	public String save(Pago pago, Model model, Authentication authentication) {
		// Setea usuario que genero el pago
		pago.setUsuario(usuarioService.findUserByUsername(authentication.getName()));
		pago.setFecha(LocalDateTime.now());
		
		pagoService.save(pago);
		return GeneralPath.REDIRECT + GeneralPath.ABM_PAGO;
	}

	@GetMapping("/deletePago/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		pagoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_PAGO;
	}
}
