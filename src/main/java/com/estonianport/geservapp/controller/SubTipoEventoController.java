package com.estonianport.geservapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estonianport.geservapp.commons.DateUtil;
import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;

@Controller
public class SubTipoEventoController {

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private TipoEventoService tipoEventoService;

	@RequestMapping("/abmSubTipoEvento")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaSubTipoEvento", subTipoEventoService.getAll());

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		return GeneralPath.SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_SUB_TIPO_EVENTO;
	}

	@GetMapping("/saveSubTipoEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {

		List<TipoEvento> listaTipoEvento = tipoEventoService.getAll();
		model.addAttribute("listaTipoEvento", listaTipoEvento);

		// Agrega lista de Hora
		model.addAttribute("listaHora", DateUtil.HORAS);

		// Agrega lista de Minuto
		model.addAttribute("listaMinuto", DateUtil.MINUTOS);

		SubTipoEvento subTipoEvento = new SubTipoEvento();
		
		List<PrecioConFecha> listaPrecioConFecha = new ArrayList<PrecioConFecha>();
		
	    for (int i = 0; i <= 11; i++) {
	    	listaPrecioConFecha.add(new PrecioConFecha());
	    }

	    subTipoEvento.setListaPrecioConFecha(listaPrecioConFecha);
	    
	    SubTipoEvento subTipoEventoEnBase = subTipoEventoService.get(id);
	    
	    if(subTipoEventoEnBase.getListaPrecioConFecha().isEmpty()) {
		    subTipoEventoEnBase.setListaPrecioConFecha(listaPrecioConFecha);
	    }

		
		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.SUB_TIPO_EVENTO, subTipoEventoEnBase);
		}else {
			model.addAttribute(GeneralPath.SUB_TIPO_EVENTO, subTipoEvento);
		}
		return GeneralPath.SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_SUB_TIPO_EVENTO;
	}

	@PostMapping("/saveSubTipoEvento")
	public String save(SubTipoEvento subTipoEvento, Model model) {
		subTipoEventoService.save(subTipoEvento);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SUB_TIPO_EVENTO;
	}

	@GetMapping("/deleteSubTipoEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		subTipoEventoService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_SUB_TIPO_EVENTO;
	}
}
