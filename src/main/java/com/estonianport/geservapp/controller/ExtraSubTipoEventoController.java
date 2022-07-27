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
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraSubTipoEventoService;
import com.estonianport.geservapp.service.PrecioConFechaExtraSubTipoEventoService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class ExtraSubTipoEventoController {

	@Autowired
	private ExtraSubTipoEventoService extraSubTipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private PrecioConFechaExtraSubTipoEventoService precioConFechaExtraSubTipoEventoService;

	@RequestMapping("/abmExtraSubTipoEvento")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaExtra", extraSubTipoEventoService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EXTRA_SUB_TIPO_EVENTO;
	}

	@GetMapping("/saveExtraSubTipoEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			ExtraSubTipoEvento extra = extraSubTipoEventoService.get(id);
			model.addAttribute("listaSubTipoEventoSeleccionadas", extra.getListaSubTipoEvento());
			model.addAttribute(GeneralPath.EXTRA, extra);
		}else {
			model.addAttribute(GeneralPath.EXTRA, new ExtraSubTipoEvento());
		}
		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EXTRA_SUB_TIPO_EVENTO;
	}

	@PostMapping("/saveExtraSubTipoEvento")
	public String save(ExtraSubTipoEvento extraSubTipoEvento, Model model) {
		extraSubTipoEventoService.save(extraSubTipoEvento);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_SUB_TIPO_EVENTO;
	}

	@GetMapping("/deleteExtraSubTipoEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {

		ExtraSubTipoEvento extra = extraSubTipoEventoService.get(id);

		// Elimina los subTipoEvento Vinculados
		extra.setListaSubTipoEvento(null);
		extraSubTipoEventoService.save(extra);


		// Elimina los precios seteados para este extra variable
		for(PrecioConFecha precioConFecha : extra.getListaPrecioConFecha()) {
			precioConFechaExtraSubTipoEventoService.delete(precioConFecha.getId());
		}

		extraSubTipoEventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_SUB_TIPO_EVENTO;
	}
}
