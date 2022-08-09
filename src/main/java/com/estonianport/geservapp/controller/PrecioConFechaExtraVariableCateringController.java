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
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.PrecioConFechaExtraVariableCatering;
import com.estonianport.geservapp.model.PrecioConFechaSubTipoEvento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraVariableCateringService;
import com.estonianport.geservapp.service.PrecioConFechaExtraVariableCateringService;

@Controller
public class PrecioConFechaExtraVariableCateringController {

	@Autowired
	private ExtraVariableCateringService extraVariableCateringService;

	@Autowired
	private PrecioConFechaExtraVariableCateringService precioConFechaExtraVariableCateringService;


	@GetMapping("/saveExtraVariableCateringPrecioConFecha/{id}")
	public String setPrecioExtraVariableCatering(@PathVariable("id") Long id, Model model, HttpSession session) {

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

		ExtraVariableCatering extraVariableCatering = extraVariableCateringService.get(id);

		List<PrecioConFecha> listaPrecioConFecha = new ArrayList<PrecioConFecha>();

		// ---------------- Agrega lista de Precio con fecha -----------------------
		if(!extraVariableCatering.getListaPrecioConFecha().isEmpty()) {
			for(PrecioConFecha precioConFecha : extraVariableCatering.getListaPrecioConFecha()) {
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
		precioConFechaEnvioContainer.setId(extraVariableCatering.getId());
		model.addAttribute(GeneralPath.CONTAINER, precioConFechaEnvioContainer);

		// Agrega el volver de donde venga, titulo y action 
		model.addAttribute(GeneralPath.TITULO, "Editar precio extra variable catering");
		model.addAttribute(GeneralPath.ACTION, "/saveExtraVariableCateringPrecioConFecha");
		model.addAttribute(GeneralPath.VOLVER, "/abmExtraCatering");

		return GeneralPath.PRECIO_CON_FECHA + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PRECIO_CON_FECHA;
	}

	@PostMapping("/saveExtraVariableCateringPrecioConFecha")
	public String saveSubTipoEventoPrecioConFecha(PrecioConFechaEnvioContainer precioConFechaEnvioContainer, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		ExtraVariableCatering ExtraVariableCatering = extraVariableCateringService.get(precioConFechaEnvioContainer.getId());
		List<PrecioConFechaExtraVariableCatering> listaPrecioConFecha = ExtraVariableCatering.getListaPrecioConFecha();

		// Elmina los precios antiguos
		for(PrecioConFecha precioConFecha : listaPrecioConFecha) {
			if(precioConFecha.getSalon().getId() == salon.getId()) {
				precioConFechaExtraVariableCateringService.delete(precioConFecha.getId());
			}
		}

		// Setea los precios nuevos
		for(PrecioConFechaContainer precioConFechaContainer : precioConFechaEnvioContainer.getPrecioConFecha()) {

			PrecioConFechaExtraVariableCatering precioConFecha = new PrecioConFechaExtraVariableCatering();
			precioConFecha.setPrecio(precioConFechaContainer.getPrecio());
			precioConFecha.setDesde(DateUtil.createFechaConHora(precioConFechaContainer.getDesde(), DateUtil.START_TIME));
			precioConFecha.setHasta(DateUtil.createFechaConHora(precioConFechaContainer.getHasta(), DateUtil.END_TIME).with(TemporalAdjusters.lastDayOfMonth()));
			precioConFecha.setSalon(salon);
			precioConFecha.setExtraVariableCatering(ExtraVariableCatering);

			precioConFechaExtraVariableCateringService.save(precioConFecha);
		}

		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_CATERING;
	}

}
