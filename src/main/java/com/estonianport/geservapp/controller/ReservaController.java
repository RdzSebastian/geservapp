package com.estonianport.geservapp.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.estonianport.geservapp.commons.CodeGenerator;
import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.EmailService;
import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.TipoCatering;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraVariableCateringService;
import com.estonianport.geservapp.service.ExtraSubTipoEventoService;
import com.estonianport.geservapp.service.ExtraVariableSubTipoEventoService;
import com.estonianport.geservapp.service.ServicioService;
import com.estonianport.geservapp.service.SexoService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoCateringService;
import com.estonianport.geservapp.service.TipoEventoService;
import com.estonianport.geservapp.service.UsuarioService;

@Controller
public class ReservaController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	@Autowired
	private ExtraSubTipoEventoService extraSubTipoEventoService;

	@Autowired
	private ExtraVariableSubTipoEventoService extraVariableSubTipoEventoService;

	@Autowired
	private TipoCateringService tipoCateringService;

	@Autowired
	private ExtraVariableCateringService extraCateringService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SexoService sexoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ServicioService servicioService;

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega lista de Hora
		model.addAttribute("listaHora", DateUtil.HORAS);

		// Agrega lista de Minuto
		model.addAttribute("listaMinuto", DateUtil.MINUTOS);

		ReservaContainer reservaContainer = new ReservaContainer();

		// Agrega lista Sexo
		model.addAttribute("listaSexo", sexoService.getAll());

		// Agrega lista Servicio
		model.addAttribute("listaServicio", servicioService.getAll());

		// Agrega lista de Tipo Eventos
		model.addAttribute("listaTipoEvento", tipoEventoService.getAll());

		// Agrega lista de Sub Tipo Eventos
		model.addAttribute("listaSubTipoEvento", subTipoEventoService.getAll());

		// Obtiene todos los Extra
		List<ExtraSubTipoEvento> listaExtra = extraSubTipoEventoService.getAll();

		// Agrega lista Extras al modelo
		model.addAttribute("listaExtra", listaExtra);

		// Agrega lista Extras a Reserva
		reservaContainer.setExtraSubTipoEvento(Set.copyOf(listaExtra));

		// Obtiene todos los Extra Variables
		List<ExtraVariableSubTipoEvento> listaExtraVariable = extraVariableSubTipoEventoService.getAll();

		// Agrega lista Extras Variables al modelo
		model.addAttribute("listaExtraVariable", listaExtraVariable);

		// Agrega lista Extras Variables a Reserva
		reservaContainer.setExtraVariableSubTipoEvento(Set.copyOf(listaExtraVariable));

		// Obtiene todos los Extra Catering
		List<ExtraVariableCatering> listaExtraCatering = extraCateringService.getAll();

		// Agrega lista Extras Catering al modelo
		model.addAttribute("listaExtraCatering", listaExtraCatering);

		// Agrega lista Extras Catering a Reserva
		reservaContainer.setExtraCatering(Set.copyOf(listaExtraCatering));

		// Obtiene todos los Tipo Catering
		List<TipoCatering> listaTipoCatering = tipoCateringService.getAll();

		// Agrega lista Tipo Catering al modelo
		model.addAttribute("listaTipoCatering", listaTipoCatering);

		// Agrega lista Tipo Catering a Reserva
		reservaContainer.setTipoCatering(Set.copyOf(listaTipoCatering));

		//		Set<EventoExtraVariableSubTipoEvento> listaEventoExtraVariableSubTipoEvento = new HashSet<EventoExtraVariableSubTipoEvento>();
		//		reservaContainer.setEventoExtraVariableSubTipoEvento(listaEventoExtraVariableSubTipoEvento);

		// Agrega reservaContainer al modelo
		model.addAttribute("reservaContainer", reservaContainer);

		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
	}

	@GetMapping("/saveEventoExtra/{id}")
	public String showSaveExtra(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		ReservaContainer reservaContainer = new ReservaContainer();

		Evento evento = eventoService.get(id);

		reservaContainer.setEvento(evento);

		// Agrega lista Extras
		model.addAttribute("listaExtra", evento.getSubTipoEvento().getListaExtraSubTipoEvento());

		// Obtiene todos los extra
		List<ExtraSubTipoEvento> listaExtra = extraSubTipoEventoService.getAll();

		// TODO Refactor para no tener que setear el null
		// Agrega lista extra seleccionadas
		for(ExtraSubTipoEvento extraSubTipoEvento : listaExtra) {
			extraSubTipoEvento.setListaSubTipoEvento(null);
		}

		// Agrega lista Extras al modelo
		model.addAttribute("listaExtraJS", listaExtra);

		// TODO Refactor para no tener que setear el null
		// Agrega lista extra seleccionadas
		Set<ExtraSubTipoEvento> listaExtraSeleccionadas = evento.getListaExtraSubTipoEvento();
		for(ExtraSubTipoEvento extraSubTipoEvento : listaExtraSeleccionadas) {
			extraSubTipoEvento.setListaSubTipoEvento(null);
		}
		model.addAttribute("listaExtraSeleccionadas", listaExtraSeleccionadas);
		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoExtra";
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
		reservaContainer.setInicio(DateUtil.getHora(evento.getStartd()));

		// Setea hora de fin
		reservaContainer.setFin(DateUtil.getHora(evento.getEndd()));

		// Setea checkbox hastaElotroDia en caso de que el dia de caundo termina del evento es 1 mas que cuando empieza
		if(evento.getStartd().plusDays(1).getDayOfMonth() == evento.getEndd().getDayOfMonth()) {
			reservaContainer.setHastaElOtroDia(true);
		}

		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoHora";
	}

	@GetMapping("/saveEventoCatering/{id}")
	public String showSaveCatering(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		Evento evento = eventoService.get(id);
		ReservaContainer reservaContainer = new ReservaContainer();
		reservaContainer.setEvento(evento);
		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoCatering";
	}
	
	@GetMapping("/saveEventoPago/{id}")
	public String showSavePago(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		Evento evento = eventoService.get(id);
		ReservaContainer reservaContainer = new ReservaContainer();
		reservaContainer.setEvento(evento);
		model.addAttribute("reservaContainer", reservaContainer);

		model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + "editEventoPago";
	}

	@PostMapping("/saveEvento")
	public String save(@ModelAttribute("reservaContainer") ReservaContainer reservaContainer, Model model, HttpSession session, Authentication authentication) {

		// El container retorna los objetos a usar
		Evento evento = reservaContainer.getEvento();

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		evento.setSalon(salon);

		// Setea fecha del evento y hora de inicio
		evento.setStartd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getInicio()));

		// Chequea si el evento es toda la noche, en caso de serlo le setea una fecha de final 1 dia despues
		if(!reservaContainer.getHastaElOtroDia()) {
			evento.setEndd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getFin()));
		}else {
			evento.setEndd(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getFin()).plusDays(1));
		}

		// Setea usuario que genero la reserva
		evento.setUsuario(usuarioService.findUserByUsername(authentication.getName()));

		// Comprueba que el evento no tenga codigo
		if(evento.getCodigo() == null || evento.getCodigo().isEmpty()){
			evento.setCodigo(generateCodigo());
		}

		// Guarda el cliente en la base de datos
		if(!clienteService.existsByCuil(reservaContainer.getCliente().getCuil())) {
			evento.setCliente(clienteService.save(reservaContainer.getCliente()));
		}else {
			evento.setCliente(clienteService.getClienteByCuil(reservaContainer.getCliente().getCuil()));
		}

		// Agrega la lista de Extras seleccionados
		evento.setListaExtraSubTipoEvento(reservaContainer.getExtraSubTipoEvento());

		// Agrega la lista de Extras Varibles seleccionados
		evento.setListaEventoExtraVariable(Set.copyOf(reservaContainer.getEventoExtraVariableSubTipoEvento()));

		// Setea el evento a cada uno de los ExtraVariable
		evento.getListaEventoExtraVariable().stream().forEach(eventoExtraVariable -> eventoExtraVariable.setEvento(evento));

		// Agrega la lista de Extras Catering
		evento.getCatering().setListaCateringExtraVariableCatering(Set.copyOf(reservaContainer.getCateringExtraVariableCatering()));

		// Setea el catering a cada uno de los ExtraVariableCatering
		evento.getCatering().getListaCateringExtraVariableCatering().stream().forEach(cateringExtraVariable -> cateringExtraVariable.setCatering(evento.getCatering()));

		// Agrega la lista de Tipo Catering
		evento.getCatering().setListaTipoCatering(reservaContainer.getTipoCatering());

		// Agrega todo el objeto TipoEvento y SubTipoEvento para envio de mail y pdf
		evento.setSubTipoEvento(subTipoEventoService.get(evento.getSubTipoEvento().getId()));

		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer);

		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

	private String generateCodigo() {
		// Crea el codigo del evento
		String codigo = CodeGenerator.getBase26Only4Letters();

		//Chequea que el codigo no este en uso 
		while(eventoService.existsByCodigo(codigo)) {
			codigo = CodeGenerator.getBase26Only4Letters();
		}
		return codigo;
	}

}
