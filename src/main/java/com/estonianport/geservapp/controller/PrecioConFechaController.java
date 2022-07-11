package com.estonianport.geservapp.controller;

import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.MesContainer;
import com.estonianport.geservapp.container.PrecioConFechaContainer;
import com.estonianport.geservapp.container.SubTipoEventoContainer;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.service.PrecioConFechaService;
import com.estonianport.geservapp.service.SubTipoEventoService;

@Controller
public class PrecioConFechaController {

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private PrecioConFechaService precioConFechaService;


	@GetMapping("/saveSubTipoEventoPrecioConFecha/{id}")
	public String setPrecio(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// ---------------- Agrega lista de Meses -----------------------
		List<MesContainer> listaMesContainer = new ArrayList<MesContainer>();

		int mesValor = 1;
		for(String mes : DateUtil.MESES) {
			MesContainer mesContainer = new MesContainer();
			mesContainer.setNombre(mes);
			mesContainer.setValor(mesValor);
			listaMesContainer.add(mesContainer);
			mesValor++;
		}


		model.addAttribute("listaMeses", listaMesContainer);
		// -----------------------------------------------------------

		SubTipoEventoContainer subTipoEventoContainer = new SubTipoEventoContainer();

		SubTipoEvento subTipoEvento = subTipoEventoService.get(id);
		
		List<PrecioConFecha> listaPrecioConFecha = new ArrayList<PrecioConFecha>();
		
		// ---------------- Agrega lista de Precio con fecha -----------------------
		if(!subTipoEvento.getListaPrecioConFecha().isEmpty()) {
			for(PrecioConFecha precioConFecha : subTipoEvento.getListaPrecioConFecha()) {
				if(precioConFecha.getSalon().getId() == salon.getId()) {
					listaPrecioConFecha.add(precioConFecha);
				}
			}
		}

		if(listaPrecioConFecha.isEmpty()){
			for (int i = 0; i <= 11; i++) {
				listaPrecioConFecha.add(new PrecioConFecha());
			}
		}
		// -----------------------------------------------------------------------
		
		// Setea la lista de precioConFecha vacia o segun el salon desde donde se busco
		subTipoEvento.setListaPrecioConFecha(listaPrecioConFecha);

		subTipoEventoContainer.setSubTipoEvento(subTipoEvento);
		model.addAttribute("subTipoEventoContainer", subTipoEventoContainer);

		return GeneralPath.SUB_TIPO_EVENTO + GeneralPath.PATH_SEPARATOR + "saveSubTipoEventoPrecioConFecha";
	}

	@PostMapping("/saveSubTipoEventoPrecioConFecha")
	public String saveSubTipoEventoPrecioConFecha(SubTipoEventoContainer subTipoEventoContainer, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		SubTipoEvento subTipoEvento = subTipoEventoContainer.getSubTipoEvento();

		// Setea fecha del evento y hora de inicio
		for(PrecioConFechaContainer precioConFechaContainer : subTipoEventoContainer.getPrecioConFecha()) {

			PrecioConFecha precioConFecha = new PrecioConFecha();
			precioConFecha.setPrecio(precioConFechaContainer.getPrecio());
			precioConFecha.setDesde(DateUtil.createFechaConHora(precioConFechaContainer.getDesde(), DateUtil.START_TIME));
			precioConFecha.setHasta(DateUtil.createFechaConHora(precioConFechaContainer.getHasta(), DateUtil.END_TIME).with(TemporalAdjusters.lastDayOfMonth()));
			precioConFecha.setSalon(salon);
			precioConFecha.setSubTipoEvento(subTipoEvento);

			precioConFechaService.save(precioConFecha);
		}

		return GeneralPath.REDIRECT + GeneralPath.ABM_SUB_TIPO_EVENTO;
	}

}
