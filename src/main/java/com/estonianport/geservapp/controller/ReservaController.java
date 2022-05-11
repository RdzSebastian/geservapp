package com.estonianport.geservapp.controller;

import java.time.LocalDateTime;
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
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
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
	private ExtraService extraService;

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
		model.addAttribute("listaHora", DateUtil.horas);

		// Agrega lista de Minuto
		model.addAttribute("listaMinuto", DateUtil.minutos);
		
		ReservaContainer reservaContainer = new ReservaContainer();

		if(id != null && id != 0) {
			Evento evento = eventoService.get(id);

			reservaContainer.setEvento(evento);

			// Setea fecha del evento
			reservaContainer.setFecha(DateUtil.getFecha(evento.getStartd()));

			// Setea hora de inicio
			reservaContainer.setInicio(DateUtil.getHora(evento.getStartd()));

			// Setea hora de fin
			reservaContainer.setFin(DateUtil.getHora(evento.getEndd()));

			// Setea checkbox hastaElotroDia
			if(reservaContainer.getFin().equals("05:00")) {
				reservaContainer.setHastaElOtroDia(true);
			}

			// Agrega lista Extras
			model.addAttribute("listaExtra", evento.getSubTipoEvento().getListaExtra());

			// Agrega lista extra seleccionadas
			Set<Extra> listaExtraSeleccionadas = evento.getListaExtra();
			for(Extra extra : listaExtraSeleccionadas) {
				extra.setListaSubTipoEvento(null);
			}
			model.addAttribute("listaExtraSeleccionadas", listaExtraSeleccionadas);
			model.addAttribute("reservaContainer", reservaContainer);

			model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
			return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.EDIT_EVENTO;
		}else {
			// Agrega lista Sexo
			model.addAttribute("listaSexo", sexoService.getAll());

			// Agrega lista Servicio
			model.addAttribute("listaServicio", servicioService.getAll());

			// Agrega lista de Tipo Eventos
			model.addAttribute("listaTipoEvento", tipoEventoService.getAll());

			// Agrega lista de Sub Tipo Eventos
			model.addAttribute("listaSubTipoEvento", subTipoEventoService.getAll());

			// Obtiene todos los extra
			List<Extra> listaExtra = extraService.getAll();

			// Agrega lista Extras al modelo
			model.addAttribute("listaExtra", listaExtra);

			// Agrega lista Extras a Reserva
			reservaContainer.setExtra(Set.copyOf(listaExtra));

			// Agrega reservaContainer al modelo
			model.addAttribute("reservaContainer", reservaContainer);

			return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_EVENTO;
		}
	}

	@PostMapping("/saveEvento")
	public String save(@ModelAttribute("reservaContainer") ReservaContainer reservaContainer, Model model, HttpSession session, Authentication authentication) {

		// El container retorna los objetos a usar
		Evento evento = reservaContainer.getEvento();

		// Salon en sesion para volver al calendario y setear en el save
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		evento.setSalon(salon);

		// Setea fecha del evento y hora de inicio
		evento.setStartd(DateUtil.createFechaConHora(reservaContainer.getFecha() + " " + reservaContainer.getInicio()));

		// Chequea si el evento es toda la noche, en vaso de serlo le setea una fecha de final 1 dia despues y a las 5am
		if(!reservaContainer.getHastaElOtroDia()) {
			evento.setEndd(DateUtil.createFechaConHora(reservaContainer.getFecha() + " " + reservaContainer.getFin()));
		}else {
			LocalDateTime fechaFin = DateUtil.createFechaConHora(reservaContainer.getFecha() + " " + "05:00");
			evento.setEndd(fechaFin.plusDays(1));
		}

		// Setea usuario que genero la reserva
		evento.setUsuario(usuarioService.findUserByUsername(authentication.getName()));

		// Comprueba que el evento no tenga codigo
		if(evento.getCodigo() == null || evento.getCodigo() == "" ){
			// Crea el codigo del evento
			String codigo = CodeGenerator.GetBase26Only4Letters();

			//Chequea que el codigo no este en uso 
			while(eventoService.existsByCodigo(codigo)) {
				codigo = CodeGenerator.GetBase26Only4Letters();
			}
			evento.setCodigo(codigo);
		}

		// Guarda el cliente en la base de datos
		if(!clienteService.existsByCuil(reservaContainer.getCliente().getCuil())) {
			clienteService.save(reservaContainer.getCliente());
			evento.setCliente(reservaContainer.getCliente());
		}else {
			evento.setCliente(clienteService.getClienteByCuil(reservaContainer.getCliente().getCuil()));
		}

		// Agrega la lista de Extras seleccionados
		evento.setListaExtra(reservaContainer.getExtra());

		// Guarda el evento en la base de datos
		eventoService.save(evento);

		// Agrega todo el objeto TipoEvento y SubTipoEvento para envio de mail y pdf
		evento.setSubTipoEvento(subTipoEventoService.get(evento.getSubTipoEvento().getId()));

		// Envia mail con comprobante
		emailService.enviarMailComprabanteReserva(reservaContainer);

		return GeneralPath.REDIRECT + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId();
	}
}
