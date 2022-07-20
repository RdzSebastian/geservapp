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
import com.estonianport.geservapp.commons.ItextService;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.CateringExtraVariableCatering;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.PagoService;
import com.estonianport.geservapp.service.ServicioService;
import com.estonianport.geservapp.service.SexoService;
import com.estonianport.geservapp.service.SubTipoEventoService;
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
	private EmailService emailService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private SexoService sexoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ServicioService servicioService;
	
	@Autowired
	private PagoService pagoService;

	@Autowired
	private ItextService itextService;

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega lista de Hora
		model.addAttribute("listaHora", DateUtil.HORAS);

		// Agrega lista de Minuto
		model.addAttribute("listaMinuto", DateUtil.MINUTOS);

		// Agrega lista Sexo
		model.addAttribute("listaSexo", sexoService.getAll());

		// Agrega lista Servicio
		model.addAttribute("listaServicio", servicioService.getAll());

		// Agrega lista de Tipo Eventos
		model.addAttribute("listaTipoEvento", tipoEventoService.getAll());

		// Agrega lista de Sub Tipo Eventos
		model.addAttribute("listaSubTipoEvento", subTipoEventoService.getAll());

		// Agrega reservaContainer al modelo
		model.addAttribute("reservaContainer", new ReservaContainer());

		return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
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

		// Comprueba que la lista de extras variables no sea null
		if(reservaContainer.getEventoExtraVariableSubTipoEvento() != null) {

			// Elimina los extras variables con cantidad 0
			List<EventoExtraVariableSubTipoEvento> listExtraVariableSubtipoEvento = reservaContainer.getEventoExtraVariableSubTipoEvento();
			listExtraVariableSubtipoEvento.removeIf(n -> n.getCantidad() == 0);

			// Agrega la lista de Extras Varibles seleccionados
			evento.setListaEventoExtraVariable(Set.copyOf(listExtraVariableSubtipoEvento));

			// Setea el evento a cada uno de los ExtraVariable
			evento.getListaEventoExtraVariable().stream().forEach(eventoExtraVariable -> eventoExtraVariable.setEvento(evento));
		}

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

		// Agrega todo el objeto TipoEvento y SubTipoEvento para envio de mail y pdf
		evento.setSubTipoEvento(subTipoEventoService.get(evento.getSubTipoEvento().getId()));

		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer, "sido reservado");

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

	@GetMapping("/deleteEvento/{id}")
	public String delete(@PathVariable("id") Long id, Model model, HttpSession session) throws Exception {
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		session.setAttribute("salon", salon);

		// Elimina el archivo pdf de comprobante
		Evento evento = eventoService.get(id);
		itextService.deletePdf(evento.getCodigo());
		
		// Borra todos los pagos de dicho evento en la base de datos
		List<Pago> listaPago = pagoService.findPagosByEvento(evento);
		for(Pago pago : listaPago) {
			pagoService.delete(pago.getId());
		}
		
		// Elimina el registro en la base de datos
		eventoService.delete(id);
		
		// TODO Enviar mail que se elimino el evento
		emailService.enviarMailEventoEliminado(evento);
		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}

}
