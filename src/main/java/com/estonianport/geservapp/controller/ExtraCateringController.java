package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estonianport.geservapp.commons.data.GeneralPath;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraVariableCateringService;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableCateringService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class ExtraCateringController {

	@Autowired
	private ExtraVariableCateringService extraCateringService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;
	
	@Autowired
	private PrecioConFechaExtraVariableCateringService precioConFechaExtraVariableCateringService;

	@RequestMapping("/abmExtraCatering")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaExtra", extraCateringService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EXTRA_CATERING;
	}

	@GetMapping("/saveExtraCatering/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			ExtraVariableCatering extraCatering = extraCateringService.get(id);
			model.addAttribute("listaSubTipoEventoSeleccionadas", extraCatering.getListaSubTipoEvento());
			model.addAttribute(GeneralPath.EXTRA, extraCatering);
		}else {
			model.addAttribute(GeneralPath.EXTRA, new ExtraVariableCatering());
		}
		return GeneralPath.CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA_CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EXTRA_CATERING;
	}

	@PostMapping("/saveExtraCatering")
	public String save(ExtraVariableCatering extraCatering, Model model) {
		extraCateringService.save(extraCatering);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_CATERING;
	}

	@GetMapping("/deleteExtraCatering/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		
		ExtraVariableCatering extraCatering = extraCateringService.get(id);
		
		// Elimina los subTipoEvento Vinculados
		extraCatering.setListaSubTipoEvento(null);
		extraCateringService.save(extraCatering);
		
		// Elimina los precios seteados para este extra variable
		for(PrecioConFecha precioConFecha : extraCatering.getListaPrecioConFecha()) {
			precioConFechaExtraVariableCateringService.delete(precioConFecha.getId());
		}
		
		extraCateringService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_CATERING;
	}
}
