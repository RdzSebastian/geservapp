package com.estonianport.geservapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.commons.ItextService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.PagoService;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private SalonService salonService;

	@Autowired
	private PagoService pagoService;

	@Autowired
	private ItextService itextService;
	
	@RequestMapping("/abmEvento/{id}")
	public String abm(@PathVariable("id") Long id, Model model, HttpSession session) {
		Salon salon = salonService.get(id);
		session.setAttribute("salon", salon);
		
		// Setea el id de salon y volver abmEvento por si selecciona una fecha
		session.setAttribute("volver", "/abmEvento/" + id);

		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO;
	}

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model, HttpSession session) throws Exception {
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		session.setAttribute("salon", salon);

		// Elimina el archivo pdf de comprobante
		Evento evento = eventoService.get(id);
		itextService.deletePdf(evento.getCodigo());
		
		// Borra todos los pagos de dicho evento en la base de datos
		List<Pago> listaPago = pagoService.findPagosByEvento(evento);
		for(Pago pago : listaPago) {
			pagoService.delete(pago.getId());
		}
		
		// Elimina el registro en la base de datos
		eventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}
}
