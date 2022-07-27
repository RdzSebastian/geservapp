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
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraVariableSubTipoEventoService;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableSubTipoEventoService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class ExtraVariableSubTipoEventoController {

	@Autowired
	private ExtraVariableSubTipoEventoService extraVariableSubTipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private PrecioConFechaExtraVariableSubTipoEventoService precioConFechaExtraVariableSubTipoEventoService;

	@RequestMapping("/abmExtraVariableSubTipoEvento")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaExtra", extraVariableSubTipoEventoService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_VARIABLE_SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EXTRA_VARIABLE_SUB_TIPO_EVENTO;
	}

	@GetMapping("/saveExtraVariableSubTipoEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			ExtraVariableSubTipoEvento extra = extraVariableSubTipoEventoService.get(id);
			model.addAttribute("listaSubTipoEventoSeleccionadas", extra.getListaSubTipoEvento());
			model.addAttribute(GeneralPath.EXTRA, extra);
		}else {
			model.addAttribute(GeneralPath.EXTRA, new ExtraVariableSubTipoEvento());
		}
		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_VARIABLE_SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EXTRA_VARIABLE_SUB_TIPO_EVENTO;
	}

	@PostMapping("/saveExtraVariableSubTipoEvento")
	public String save(ExtraVariableSubTipoEvento extraVariableSubTipoEvento, Model model) {
		extraVariableSubTipoEventoService.save(extraVariableSubTipoEvento);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_VARIABLE_SUB_TIPO_EVENTO;
	}

	@GetMapping("/deleteExtraVariableSubTipoEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {

		ExtraVariableSubTipoEvento extraVariable = extraVariableSubTipoEventoService.get(id);

		// Elimina los subTipoEvento Vinculados
		extraVariable.setListaSubTipoEvento(null);
		extraVariableSubTipoEventoService.save(extraVariable);

		// Elimina los precios seteados para este extra variable
		for(PrecioConFecha precioConFecha : extraVariable.getListaPrecioConFecha()) {
			precioConFechaExtraVariableSubTipoEventoService.delete(precioConFecha.getId());
		}

		extraVariableSubTipoEventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_VARIABLE_SUB_TIPO_EVENTO;
	}
}
