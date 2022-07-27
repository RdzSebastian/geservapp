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

import com.estonianport.geservapp.commons.EmailService;
import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.MedioDePago;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.MedioDePagoService;
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

	@Autowired
	private MedioDePagoService medioDePagoService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("/abmPago")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaPago", pagoService.getAll());
		session.setAttribute(GeneralPath.ACTION, "/savePago/0");
		session.setAttribute(GeneralPath.VOLVER, "../" + GeneralPath.ABM_PAGO);

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		session.setAttribute(GeneralPath.TITULO, "Agregar Pago");

		return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_PAGO;
	}

	@PostMapping("/savePago/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, CodigoContainer codigoContainer, HttpSession session) {

		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);

		model.addAttribute(GeneralPath.TITULO, session.getAttribute(GeneralPath.TITULO));
		model.addAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));
		Pago pago = new Pago();

		List<MedioDePago> listaMedioDePago = medioDePagoService.getAll();
		model.addAttribute("listaMedioDePago", listaMedioDePago);

		if(eventoService.existsByCodigo(codigoContainer.getCodigo())) {
			pago.setEvento(eventoService.getEventoByCodigo(codigoContainer.getCodigo()));
			model.addAttribute(GeneralPath.PAGO, pago);
			return GeneralPath.PAGO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PAGO;
		}

		// Setea el valor de volver cuando termine de guardar
		session.setAttribute(GeneralPath.VOLVER + GeneralPath.ACTION, "saveEventoPago/" + pago.getEvento().getId());

		model.addAttribute("eventoNoEncontrado", true);
		return GeneralPath.EVENTO + "/buscarEvento";

	}

	@PostMapping("/savePago")
	public String save(Pago pago, Model model, Authentication authentication, HttpSession session) {
		// Setea usuario que genero el pago
		pago.setUsuario(usuarioService.findUserByUsername(authentication.getName()));
		pago.setFecha(LocalDateTime.now());

		pagoService.save(pago);

		// Envia mail con comprobante
		List<Pago> listaPagos = pagoService.findPagosByEvento(pago.getEvento());
		emailService.enviarMailComprabantePago(pago, listaPagos);

		// Obtiene el valor de volver cuando termine de guardar
		return GeneralPath.REDIRECT + session.getAttribute(GeneralPath.VOLVER + GeneralPath.ACTION);
	}

	@GetMapping("/deletePago/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		pagoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_PAGO;
	}
}
