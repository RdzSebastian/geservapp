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
import com.estonianport.geservapp.container.PrecioConFechaEnvioContainer;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.PrecioConFechaExtraSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFechaSubTipoEvento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ExtraSubTipoEventoService;
import com.estonianport.geservapp.service.PrecioConFechaExtraSubTipoEventoService;

@Controller
public class PrecioConFechaExtraSubTipoEventoController {

	@Autowired
	private ExtraSubTipoEventoService extraSubTipoEventoService;

	@Autowired
	private PrecioConFechaExtraSubTipoEventoService precioConFechaService;


	@GetMapping("/saveExtraSubTipoEventoPrecioConFecha/{id}")
	public String setPrecioExtraSubTipoEvento(@PathVariable("id") Long id, Model model, HttpSession session) {

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

		ExtraSubTipoEvento extraSubTipoEvento = extraSubTipoEventoService.get(id);

		List<PrecioConFecha> listaPrecioConFecha = new ArrayList<PrecioConFecha>();

		// ---------------- Agrega lista de Precio con fecha -----------------------
		if(!extraSubTipoEvento.getListaPrecioConFecha().isEmpty()) {
			for(PrecioConFecha precioConFecha : extraSubTipoEvento.getListaPrecioConFecha()) {
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
		precioConFechaEnvioContainer.setId(extraSubTipoEvento.getId());
		model.addAttribute(GeneralPath.CONTAINER, precioConFechaEnvioContainer);

		// Agrega el volver de donde venga, titulo y action 
		model.addAttribute(GeneralPath.TITULO, "Editar precio Extra");
		model.addAttribute(GeneralPath.ACTION, "/saveExtraSubTipoEventoPrecioConFecha");
		model.addAttribute(GeneralPath.VOLVER, "/abmExtraSubTipoEvento");

		return GeneralPath.PRECIO_CON_FECHA + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_PRECIO_CON_FECHA;
	}

	@PostMapping("/saveExtraSubTipoEventoPrecioConFecha")
	public String saveSubTipoEventoPrecioConFecha(PrecioConFechaEnvioContainer precioConFechaEnvioContainer, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		ExtraSubTipoEvento extraSubTipoEvento = extraSubTipoEventoService.get(precioConFechaEnvioContainer.getId());
		List<PrecioConFechaExtraSubTipoEvento> listaPrecioConFecha = extraSubTipoEvento.getListaPrecioConFecha();

		// Elmina los precios antiguos
		for(PrecioConFecha precioConFecha : listaPrecioConFecha) {
			if(precioConFecha.getSalon().getId() == salon.getId()) {
				precioConFechaService.delete(precioConFecha.getId());
			}
		}

		// Setea los precios nuevos
		for(PrecioConFechaContainer precioConFechaContainer : precioConFechaEnvioContainer.getPrecioConFecha()) {

			PrecioConFechaExtraSubTipoEvento precioConFecha = new PrecioConFechaExtraSubTipoEvento();
			precioConFecha.setPrecio(precioConFechaContainer.getPrecio());
			precioConFecha.setDesde(DateUtil.createFechaConHora(precioConFechaContainer.getDesde(), DateUtil.START_TIME));
			precioConFecha.setHasta(DateUtil.createFechaConHora(precioConFechaContainer.getHasta(), DateUtil.END_TIME).with(TemporalAdjusters.lastDayOfMonth()));
			precioConFecha.setSalon(salon);
			precioConFecha.setExtraSubTipoEvento(extraSubTipoEvento);

			precioConFechaService.save(precioConFecha);
		}

		return GeneralPath.REDIRECT + GeneralPath.ABM_EXTRA_SUB_TIPO_EVENTO;
	}

}
