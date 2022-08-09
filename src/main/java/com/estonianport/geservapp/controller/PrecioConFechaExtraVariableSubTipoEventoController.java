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

import com.estonianport.geservapp.commons.data.GeneralPath;
import com.estonianport.geservapp.commons.dateUtil.DateUtil;
import com.estonianport.geservapp.container.MesContainer;
import com.estonianport.geservapp.container.PrecioConFechaContainer;
import com.estonianport.geservapp.container.PrecioConFechaEnvioContainer;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.PrecioConFechaExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFechaSubTipoEvento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraVariableSubTipoEventoService;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableSubTipoEventoService;

@Controller
public class PrecioConFechaExtraVariableSubTipoEventoController {

	@Autowired
	private ExtraVariableSubTipoEventoService extraVariableSubTipoEventoService;

	@Autowired
	private PrecioConFechaExtraVariableSubTipoEventoService precioConFechaExtraVariableSubTipoEventoService;


	@GetMapping("/saveExtraVariableSubTipoEventoPrecioConFecha/{id}")
	public String setPrecioExtraVariable(@PathVariable("id") Long id, Model model, HttpSession session) {

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

		PrecioConFechaEnvioContainer precioConFechaEnvioContainer = new PrecioConFechaEnvioContainer();

		ExtraVariableSubTipoEvento ExtraVariableSubTipoEvento = extraVariableSubTipoEventoService.get(id);

		List<PrecioConFecha> listaPrecioConFecha = new ArrayList<PrecioConFecha>();

		// ---------------- Agrega lista de Precio con fecha -----------------------
		if(!ExtraVariableSubTipoEvento.getListaPrecioConFecha().isEmpty()) {
			for(PrecioConFecha precioConFecha : ExtraVariableSubTipoEvento.getListaPrecioConFecha()) {
				if(precioConFecha.getSalon().getId() == salon.getId()) {
					listaPrecioConFecha.add(precioConFecha);
				}
			}
		}

		if(listaPrecioConFecha.isEmpty()){
			for (int i = 0; i <= 11; i++) {
				listaPrecioConFecha.add(new PrecioConFechaSubTipoEvento());
			}
		}
		// -----------------------------------------------------------------------

		// Setea la lista de precioConFecha vacia o segun el salon desde donde se busco
		model.addAttribute("listaPrecioConFecha", listaPrecioConFecha);

		// Setea el object container para el model
		precioConFechaEnvioContainer.setId(ExtraVariableSubTipoEvento.getId());
		model.addAttribute(GeneralPath.CONTAINER, precioConFechaEnvioContainer);

		// Agrega el volver de donde venga, titulo y action 
		model.addAttribute(GeneralPath.TITULO, "Editar precio Extra variable");
		model.addAttribute(GeneralPath.ACTION, "/saveExtraVariableSubTipoEventoPrecioConFecha");
		model.addAttribute(GeneralPath.VOLVER, "/abmExtraVariableSubTipoEvento");

		return GeneralPath.PRECIO_CON_FECHA + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PRECIO_CON_FECHA;
	}

	@PostMapping("/saveExtraVariableSubTipoEventoPrecioConFecha")
	public String saveExtraVariableSubTipoEventoPrecioConFecha(PrecioConFechaEnvioContainer precioConFechaEnvioContainer, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		ExtraVariableSubTipoEvento extraVariableSubTipoEvento = extraVariableSubTipoEventoService.get(precioConFechaEnvioContainer.getId());
		List<PrecioConFechaExtraVariableSubTipoEvento> listaPrecioConFecha = extraVariableSubTipoEvento.getListaPrecioConFecha();

		// Elmina los precios antiguos
		for(PrecioConFecha precioConFecha : listaPrecioConFecha) {
			if(precioConFecha.getSalon().getId() == salon.getId()) {
				precioConFechaExtraVariableSubTipoEventoService.delete(precioConFecha.getId());
			}
		}

		// Setea los precios nuevos
		for(PrecioConFechaContainer precioConFechaContainer : precioConFechaEnvioContainer.getPrecioConFecha()) {

			PrecioConFechaExtraVariableSubTipoEvento precioConFecha = new PrecioConFechaExtraVariableSubTipoEvento();
			precioConFecha.setPrecio(precioConFechaContainer.getPrecio());
			precioConFecha.setDesde(DateUtil.createFechaConHora(precioConFechaContainer.getDesde(), DateUtil.START_TIME));
			precioConFecha.setHasta(DateUtil.createFechaConHora(precioConFechaContainer.getHasta(), DateUtil.END_TIME).with(TemporalAdjusters.lastDayOfMonth()));
			precioConFecha.setSalon(salon);
			precioConFecha.setExtraVariableSubTipoEvento(extraVariableSubTipoEvento);

			precioConFechaExtraVariableSubTipoEventoService.save(precioConFecha);
		}

		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_VARIABLE_SUB_TIPO_EVENTO;
	}

}
