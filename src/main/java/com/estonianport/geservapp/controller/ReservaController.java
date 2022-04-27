package com.estonianport.geservapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.estonianport.geservapp.commons.EmailService;
import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.Sexo;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.model.TipoEvento;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
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

	@GetMapping("/saveEvento/{id}")
	public String showSave(@PathVariable("id") Long id, Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega lista Extras
		List<Extra> listaExtra = extraService.getAll();
		model.addAttribute("listaExtra", listaExtra);

		ReservaContainer reservaContainer = new ReservaContainer();

		if(id != null && id != 0) {
			Evento evento = eventoService.get(id);

			reservaContainer.setEvento(evento);

			// Setea la hora y fecha del evento
			String fecha = evento.getStartd().getDayOfMonth() + "-" + evento.getStartd().getMonth().getValue() + "-" + evento.getStartd().getYear();
			reservaContainer.setFecha(fecha);
			String horaInicio = String.valueOf(evento.getStartd().getHour()) + ":" +String.valueOf(evento.getStartd().getMinute());
			reservaContainer.setInicio(horaInicio);
			String horaFin = String.valueOf(evento.getEndd().getHour()) + ":" +String.valueOf(evento.getEndd().getMinute());
			reservaContainer.setFin(horaFin);
			
			//TODO Hace esto porque subTipoEvento, Extra y Evento se volvio recursivo 
			Set<Extra> listesub = evento.getListaExtra();
			List<Extra> list = new ArrayList<Extra>();
			
			for(Extra subt :  listesub) {
				subt.setEvento(null);
				subt.setListaSubTipoEvento(null);
				list.add(subt);
			}
			
			model.addAttribute("listaExtraSeleccionadas", list);
			model.addAttribute("reservaContainer", reservaContainer);

			model.addAttribute("volver", "../" + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());
			return GeneralPath.EVENTO + GeneralPath.PATH_SEPARATOR + GeneralPath.EDIT_EVENTO;
		}else {
			// Agrega lista Sexo
			List<Sexo> listaSexo = sexoService.getAll();
			model.addAttribute("listaSexo", listaSexo);

			// Agrega lista de Tipo Eventos
			List<TipoEvento> listaTipoEvento = tipoEventoService.getAll();
			model.addAttribute("listaTipoEvento", listaTipoEvento);

			// Agrega lista de Sub Tipo Eventos
			//TODO Hace esto porque subTipoEvento, Extra y Evento se volvio recursivo 
			List<SubTipoEvento> listaSubTipoEvento = subTipoEventoService.getAll();
			List<SubTipoEvento> listaSub = new ArrayList<SubTipoEvento>();
			for(SubTipoEvento subt :  listaSubTipoEvento) {
				subt.setListaExtra(null);
				listaSub.add(subt);
			}
			
			model.addAttribute("listaSubTipoEvento", listaSub);

			reservaContainer.setExtra(Set.copyOf(listaExtra));
			
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

		// Setea la hora y fecha del evento
		try{
			evento.setStartd(LocalDateTime.parse(reservaContainer.getFecha() + " " + reservaContainer.getInicio(), DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm")));
		}catch(Exception e){
			evento.setStartd(LocalDateTime.parse(reservaContainer.getFecha() + " " + reservaContainer.getInicio(), DateTimeFormatter.ofPattern("dd-M-yyyy HH:m")));
		}

		// Chequea si el evento es toda la noche, en vaso de serlo le setea una fecha de final 1 dia despues y a las 5am
		if(!reservaContainer.getHastaElOtroDia()) {
			try {
				evento.setEndd(LocalDateTime.parse(reservaContainer.getFecha() + " " + reservaContainer.getFin(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
			}catch(Exception e){
				evento.setEndd(LocalDateTime.parse(reservaContainer.getFecha() + " " + reservaContainer.getFin(), DateTimeFormatter.ofPattern("dd-M-yyyy HH:m")));
			}
		}else {
			LocalDateTime fechaFin = null;
			try {
				fechaFin = LocalDateTime.parse(reservaContainer.getFecha() + " " + "05:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
			}catch(Exception e){
				fechaFin = LocalDateTime.parse(reservaContainer.getFecha() + " " + "05:00", DateTimeFormatter.ofPattern("dd-M-yyyy HH:m"));
			}
			evento.setEndd(fechaFin.plusDays(1));
		}

		// Setea usuario que genero la reserva
		evento.setUsuario(usuarioService.findUserByUsername(authentication.getName()));

		if(evento.getCodigo() == null || evento.getCodigo() == "" ){
			// Crea el codigo del evento
			evento.setCodigo(CodeGenerator.GetBase26Only4Letters());
			
			//TODO Chequea que el codigo no este en uso 
		}

		// Guarda el cliente en la base de datos
		if(reservaContainer.getEvento().getCliente() == null) {
			clienteService.save(reservaContainer.getCliente());
			evento.setCliente(clienteService.get(reservaContainer.getCliente().getId()));
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
