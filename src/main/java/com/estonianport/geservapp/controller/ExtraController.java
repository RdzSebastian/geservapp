package com.estonianport.geservapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.service.ExtraService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class ExtraController {

	@Autowired
	private ExtraService extraService;
	
	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@RequestMapping("/abmExtra")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaExtra", extraService.getAll());
		
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EXTRA;
	}

	@GetMapping("/saveExtra/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("listaSubTipoEventoCompleta", subTipoEventoService.getAll());

		if(id != null && id != 0) {
			Extra extra = extraService.get(id);
			
			//TODO Hace esto porque subTipoEvento, Extra y Evento se volvio recursivo 
			Set<SubTipoEvento> listesub = extra.getListaSubTipoEvento();
			List<SubTipoEvento> list = new ArrayList<SubTipoEvento>();
			
			for(SubTipoEvento subt : listesub) {
				subt.setListaExtra(null);
				subt.setTipoEvento(null);
				list.add(subt);
			}
			
			model.addAttribute("listaSubTipoEventoSeleccionadas", list);
			model.addAttribute(GeneralPath.EXTRA, extra);
		}else {
			model.addAttribute(GeneralPath.EXTRA, new Extra());
		}
		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EXTRA;
	}

	@PostMapping("/saveExtra")
	public String save(Extra extra, Model model) {
		extraService.save(extra);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA;
	}

	@GetMapping("/deleteExtra/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		extraService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA;
	}
}
