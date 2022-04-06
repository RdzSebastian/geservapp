package com.estonianport.geservapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.EventoExtraContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtra;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;

@Controller
public class EventoExtraContainerController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private ExtraService extraService;

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, HttpSession session) {

		List<TipoEvento> listaTipoEvento = tipoEventoService.getAll();
		model.addAttribute("listaTipoEvento", listaTipoEvento);

		List<SubTipoEvento> listaSubTipoEvento = subTipoEventoService.getAll();
		model.addAttribute("listaSubTipoEvento", listaSubTipoEvento);

		List<Extra> listaExtra = extraService.getAll();
		model.addAttribute("listaExtra", listaExtra);

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		if(id != null && id != 0) {
			model.addAttribute("eventoExtraContainer", "");
		}else {
			// Crea una instancia de EventoExtraContainer para agregar todos los Extra en la vista
			EventoExtraContainer eventoExtraContainer = new EventoExtraContainer();
			eventoExtraContainer.setExtra(new ArrayList<>());
			for(Extra extra : listaExtra) {
				eventoExtraContainer.getExtra().add(extra);
			}
			model.addAttribute("eventoExtraContainer", eventoExtraContainer);
		}
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
	}

	@PostMapping("/saveEvento")
	public String save(@ModelAttribute("eventoExtraContainer") EventoExtraContainer eventoExtraContainer, Model model, HttpSession session) {

		// El container retorna los objetos a usar
		Evento evento = eventoExtraContainer.getEvento();
		List<Extra> listaExtra = eventoExtraContainer.getExtra();

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		evento.setSalon(salon);

		// Crea el Set EventoExtra para agregar los Extra y luego a Evento
		Set<EventoExtra> setEventoExtra = new HashSet<EventoExtra>();
		EventoExtra eventoExtra = null;

		if(listaExtra != null && !listaExtra.isEmpty()) {
			for(Extra extra : listaExtra) {
				eventoExtra = new EventoExtra();
				eventoExtra.setExtra(extra);
				setEventoExtra.add(eventoExtra);
			}
		}

		// Guarda el Set EventoExtra que contiene los Extras seleccionados en la vista
		evento.setEventoExtra(setEventoExtra);

		eventoService.save(evento);
		
		evento.setTipoEvento(tipoEventoService.get(evento.getTipoEvento().getId()));
		evento.setSubTipoEvento(subTipoEventoService.get(evento.getSubTipoEvento().getId()));

		eventoService.enviarMailComprabanteReserva(evento, listaExtra);
		
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}
}
