package com.estonianport.geservapp.controller;

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

import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.EmailService;
import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.CateringExtraVariableCatering;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.TipoCatering;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.PagoService;

@Controller
public class EditEventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private PagoService pagoService;

	@Autowired
	private EmailService emailService;

	// TODO Refactor para no tener que setear el null en cada una de los extras por subTipoEvento
	@GetMapping("/saveEventoExtra/{id}")
	public String showSaveExtra(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		ReservaContainer reservaContainer = new ReservaContainer();

		Evento evento = eventoService.get(id);

		reservaContainer.setEvento(evento);

		// ------------------------- Extra ------------------------------

		// Agrega lista Extras
		Set<ExtraSubTipoEvento> listaExtra = evento.getSubTipoEvento().getListaExtraSubTipoEvento();

		for(ExtraSubTipoEvento extraSubTipoEvento : listaExtra) {
			extraSubTipoEvento.setListaSubTipoEvento(null);
		}

		model.addAttribute("listaExtra", listaExtra);

		// Agrega lista extra seleccionadas
		Set<ExtraSubTipoEvento> listaExtraSeleccionadas = evento.getListaExtraSubTipoEvento();

		for(ExtraSubTipoEvento extraSubTipoEvento : listaExtraSeleccionadas) {
			extraSubTipoEvento.setListaSubTipoEvento(null);
		}

		model.addAttribute("listaExtraSeleccionadas", listaExtraSeleccionadas);


		// ------------------------- Extra variable ------------------------------
		Set<ExtraVariableSubTipoEvento> listaExtraVariable = evento.getSubTipoEvento().getListaExtraVariableSubTipoEvento();

		for(ExtraVariableSubTipoEvento extraVariableSubTipoEvento : listaExtraVariable){
			extraVariableSubTipoEvento.setListaSubTipoEvento(null);
		}

		// Agrega lista Extras Variables
		model.addAttribute("listaExtraVariable", listaExtraVariable);

		// Agrega lista extra seleccionadas
		Set<EventoExtraVariableSubTipoEvento> listaEventoExtraVariableSeleccionadas = evento.getListaEventoExtraVariable();
		Set<ExtraVariableSubTipoEvento> listaExtraVariableSeleccionadas = new HashSet<ExtraVariableSubTipoEvento>();

		for(EventoExtraVariableSubTipoEvento EventoExtraVariableSubTipoEvento : listaEventoExtraVariableSeleccionadas) {
			EventoExtraVariableSubTipoEvento.getExtraVariableSubTipoEvento().setListaSubTipoEvento(null);
			listaExtraVariableSeleccionadas.add(EventoExtraVariableSubTipoEvento.getExtraVariableSubTipoEvento());
		}

		model.addAttribute("listaExtraVariableSeleccionadas", listaExtraVariableSeleccionadas);

		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoExtra";
	}

	@PostMapping("/saveEventoExtra")
	public String saveEventoExtra(@ModelAttribute("reservaContainer") ReservaContainer reservaContainer, Model model, HttpSession session) {

		// El container retorna los objetos a usar
		Evento evento =  eventoService.get(reservaContainer.getEvento().getId());

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);

		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer);

		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

	@GetMapping("/saveEventoHora/{id}")
	public String showSaveHora(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega lista de Hora
		model.addAttribute("listaHora", DateUtil.HORAS);

		// Agrega lista de Minuto
		model.addAttribute("listaMinuto", DateUtil.MINUTOS);

		ReservaContainer reservaContainer = new ReservaContainer();

		Evento evento = eventoService.get(id);

		reservaContainer.setEvento(evento);

		// Setea fecha del evento
		reservaContainer.setFecha(DateUtil.getFecha(evento.getStartd()));

		// Setea hora de inicio
		reservaContainer.setInicio(DateUtil.getHorario(evento.getStartd()));

		// Setea hora de fin
		reservaContainer.setFin(DateUtil.getHorario(evento.getEndd()));

		// Setea checkbox hastaElotroDia en caso de que el dia de caundo termina del evento es 1 mas que cuando empieza
		if(evento.getStartd().plusDays(1).getDayOfMonth() == evento.getEndd().getDayOfMonth()) {
			reservaContainer.setHastaElOtroDia(true);
		}

		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoHora";
	}


	@PostMapping("/saveEventoHora")
	public String saveEventoHora(@ModelAttribute("reservaContainer") ReservaContainer reservaContainer, Model model, HttpSession session) {

		// El container retorna los objetos a usar
		Evento evento =  eventoService.get(reservaContainer.getEvento().getId());

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);

		// Setea fecha del evento y hora de inicio
		evento.setStartd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getInicio()));

		// Chequea si el evento es toda la noche, en caso de serlo le setea una fecha de final 1 dia despues
		if(!reservaContainer.getHastaElOtroDia()) {
			evento.setEndd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getFin()));
		}else {
			evento.setEndd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getFin()).plusDays(1));
		}

		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer);

		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

	@GetMapping("/saveEventoCatering/{id}")
	public String showSaveCatering(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		Evento evento = eventoService.get(id);

		ReservaContainer reservaContainer = new ReservaContainer();
		reservaContainer.setEvento(evento);

		Set<ExtraVariableCatering> listaExtraVariableCatering = evento.getSubTipoEvento().getListaExtraVariableCatering();

		for(ExtraVariableCatering extraVariableCatering : listaExtraVariableCatering) {
			extraVariableCatering.setListaSubTipoEvento(null);
		}

		model.addAttribute("listaExtraCatering", listaExtraVariableCatering);

		Set<CateringExtraVariableCatering> listaCateringExtraCateringSeleccionadas = evento.getCatering().getListaCateringExtraVariableCatering();
		Set<ExtraVariableCatering> listaExtraVariableCateringSeleccionadas = new HashSet<ExtraVariableCatering>();

		for(CateringExtraVariableCatering cateringExtraVariableCatering : listaCateringExtraCateringSeleccionadas) {
			cateringExtraVariableCatering.getExtraVariableCatering().setListaSubTipoEvento(null);
			listaExtraVariableCateringSeleccionadas.add(cateringExtraVariableCatering.getExtraVariableCatering());
		}

		model.addAttribute("listaExtraCateringSeleccionadas", listaExtraVariableCateringSeleccionadas);

		Set<TipoCatering> listaTipoCatering = evento.getSubTipoEvento().getListaTipoCatering();

		for(TipoCatering tipoCatering : listaTipoCatering) {
			tipoCatering.setListaSubTipoEvento(null);
		}

		model.addAttribute("listaTipoCatering", listaTipoCatering);

		Set<TipoCatering> listaTipoCateringSeleccionadas = evento.getCatering().getListaTipoCatering();

		for(TipoCatering tipoCatering : listaTipoCateringSeleccionadas) {
			tipoCatering.setListaSubTipoEvento(null);
		}

		model.addAttribute("listaTipoCateringSeleccionadas", listaTipoCateringSeleccionadas);

		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoCatering";
	}

	@PostMapping("/saveEventoCatering")
	public String saveEventoCatering(@ModelAttribute("reservaContainer") ReservaContainer reservaContainer, Model model, HttpSession session) {

		// El container retorna los objetos a usar
		Evento evento =  eventoService.get(reservaContainer.getEvento().getId());

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);

		// Setea el presupuesto y catering otro
		evento.setCatering(reservaContainer.getEvento().getCatering());
		
		// Comprueba que la lista de extras variables no sea null
		if(reservaContainer.getCateringExtraVariableCatering() != null) {

			// Elimina los extras variables con cantidad 0
			List<CateringExtraVariableCatering> listExtraVariableCatering = reservaContainer.getCateringExtraVariableCatering();
			listExtraVariableCatering.removeIf(n -> n.getCantidad() == 0);

			// Agrega la lista de Extras Catering
			evento.getCatering().setListaCateringExtraVariableCatering(Set.copyOf(reservaContainer.getCateringExtraVariableCatering()));

			// Setea el catering a cada uno de los ExtraVariableCatering
			evento.getCatering().getListaCateringExtraVariableCatering().stream().forEach(cateringExtraVariable -> cateringExtraVariable.setCatering(evento.getCatering()));
		}

		// Agrega la lista de Tipo Catering
		evento.getCatering().setListaTipoCatering(reservaContainer.getTipoCatering());
		
		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer);

		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

	@GetMapping("/saveEventoPago/{id}")
	public String showSavePago(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		Evento evento = eventoService.get(id);
		evento.setSubTipoEvento(null);
		evento.setListaEventoExtraVariable(null);
		evento.setListaExtraSubTipoEvento(null);
		evento.setCatering(null);

		ReservaContainer reservaContainer = new ReservaContainer();
		reservaContainer.setEvento(evento);

		model.addAttribute("reservaContainer", reservaContainer);

		List<Pago> listaPagos = pagoService.findPagosByEvento(evento);

		for(Pago pago : listaPagos) {
			pago.setEvento(null);
		}

		model.addAttribute("listaPagos", listaPagos);


		CodigoContainer codigoContainer = new CodigoContainer();
		codigoContainer.setCodigo(evento.getCodigo());

		model.addAttribute("codigoContainer", codigoContainer);	

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoPago";
	}

}
