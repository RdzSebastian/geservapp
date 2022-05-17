package com.estonianport.geservapp.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.commons.ItextService;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
import com.estonianport.geservapp.service.PagoService;
import com.estonianport.geservapp.service.RolService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.ServicioService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;
import com.estonianport.geservapp.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	SalonService salonService;

	@Autowired
	EventoService eventoService;

	@Autowired
	UsuarioService usuariosService;

	@Autowired
	TipoEventoService tipoEventoService;

	@Autowired
	SubTipoEventoService subTipoEventoService;

	@Autowired
	PagoService pagoService;

	@Autowired
	ExtraService extraService;

	@Autowired
	ItextService itextService;

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ServicioService servicioService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@RequestMapping("/")
	public String index(Model model) {
		List<Salon> listaSalones = salonService.getAll();
		model.addAttribute("listaSalones", listaSalones);
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/administracion")
	public String adm(Model model, HttpSession session, Authentication authentication) {
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		model.addAttribute("cantUsuario", usuariosService.count());
		model.addAttribute("cantTipoEvento", tipoEventoService.count());
		model.addAttribute("cantSubTipoEvento", subTipoEventoService.count());
		model.addAttribute("cantPago", pagoService.count());
		model.addAttribute("cantSalon", salonService.count());
		model.addAttribute("cantExtra", extraService.count());
		model.addAttribute("cantEvento", eventoService.count());
		model.addAttribute("cantCliente", clienteService.count());
		model.addAttribute("cantServicio", servicioService.count());

		model.addAttribute("admin", usuarioService.findUserByUsername(authentication.getName()).getRol() == rolService.getRolByNombre("ADMIN"));

		return "adm/adm";
	}

	@RequestMapping("/download/0")
	public String descargarComprobante(Model model, HttpSession session){
		model.addAttribute("codigoContainer", new CodigoContainer());

		// Trae valor de eventoNoEncontrado en caso de venir de /download y luego lo limpia
		model.addAttribute("eventoNoEncontrado", session.getAttribute("eventoNoEncontrado"));
		session.setAttribute("eventoNoEncontrado", null);

		// Agrega el volver de donde venga y action 
		model.addAttribute(GeneralPath.TITULO, "Descargar comprobante");
		model.addAttribute(GeneralPath.ACTION, "/download");
		model.addAttribute(GeneralPath.VOLVER, "/administracion");

		return "evento/buscarEvento";		
	}


	@RequestMapping("/buscarEvento")
	public String buscarEvento(Model model, HttpSession session){

		model.addAttribute("codigoContainer", new CodigoContainer());

		// Agrega el titulo de la busqueda que sea
		model.addAttribute(GeneralPath.TITULO, session.getAttribute(GeneralPath.TITULO));
		model.addAttribute(GeneralPath.ACTION, session.getAttribute(GeneralPath.ACTION));
		model.addAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));

		return "evento/buscarEvento";
	}

	@RequestMapping("/seleccionarFecha")
	public String buscarEvento(Model model, @RequestParam("arr") String fecha, HttpSession session){

		LocalDateTime start_date = DateUtil.createFechaInvertidaConHora(fecha, DateUtil.START_TIME);
		LocalDateTime end_date = DateUtil.createFechaInvertidaConHora(fecha, DateUtil.END_TIME);

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega el volver de donde venga
		model.addAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));

		List<Evento> listaEvento = eventoService.findAllByStartdBetweenAndSalon(start_date, end_date, salon);
		model.addAttribute("listaEvento", listaEvento);


		return "seleccionarFecha/abmSeleccionarFecha";
	}

	@RequestMapping("/buscarAllEvento")
	public String buscarAllEvento(Model model, HttpSession session){

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega el volver de donde venga y action 
		session.setAttribute(GeneralPath.TITULO, "Buscar evento");
		session.setAttribute(GeneralPath.ACTION, "/eventoEncontrado");
		session.setAttribute(GeneralPath.VOLVER, "/buscarAllEvento");

		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);
		model.addAttribute(GeneralPath.VOLVER, "/administracion");

		return "seleccionarFecha/abmSeleccionarFecha";
	}

	@RequestMapping("/eventoEncontrado")
	public String eventoEncontrado(Model model, HttpSession session, CodigoContainer codigoContainer){
		List<Evento> listaEvento = new ArrayList<Evento>();
		model.addAttribute(GeneralPath.TITULO, session.getAttribute(GeneralPath.TITULO));
		model.addAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));

		if(eventoService.existsByCodigo(codigoContainer.getCodigo())) {
			listaEvento.add(eventoService.getEventoByCodigo(codigoContainer.getCodigo()));
			model.addAttribute("listaEvento",listaEvento);
			return "seleccionarFecha/abmSeleccionarFecha";
		}

		model.addAttribute("eventoNoEncontrado", true);
		return "evento/buscarEvento";
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<FileSystemResource> download(Model model, CodigoContainer codigoContainer, HttpSession session, HttpServletResponse response) throws IOException{

		if(eventoService.existsByCodigo(codigoContainer.getCodigo())) {
			// Busca el evento por codigo y si no existe no devuelve nada
			Evento evento = eventoService.getEventoByCodigo(codigoContainer.getCodigo());
			if(evento != null) {
				try {
					File file = new File(GeneralPath.DIRECTORY_PDF + codigoContainer.getCodigo() + GeneralPath.EXTENSION_PDF);

					// Crea el archivo si no existe
					if(!file.exists()){
						ReservaContainer reservaContainer = new ReservaContainer();
						reservaContainer.setEvento(evento);

						itextService.createPdf(reservaContainer);
					}

					// Prepara archivo para descarga
					HttpHeaders respHeaders = new HttpHeaders();
					respHeaders.setContentType(MediaType.APPLICATION_PDF);
					respHeaders.setContentLength(file.length());
					respHeaders.setContentDispositionFormData("attachment", file.getName());

					return new ResponseEntity<FileSystemResource>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
		}

		// Setea el valor de no encontrado
		session.setAttribute("eventoNoEncontrado", true);

		// Hace que al devolver not found se quede en la pantalla buscar
		response.sendRedirect("download/0");

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/buscarClientePorCuil")
	public @ResponseBody ResponseEntity<Cliente> buscarClientePorCuil(Model model, @RequestParam(value="cuil") long cuil){
		
		if(clienteService.existsByCuil(cuil) && cuil != 0) {
			Cliente cliente = clienteService.getClienteByCuil(cuil);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/horarioDisponible")
	public @ResponseBody ResponseEntity<Boolean> horarioDisponible(Model model, HttpSession session, ReservaContainer reservaContainer){
		
		Salon salon =  (Salon) session.getAttribute(GeneralPath.SALON);
		
		// Crea la hora inicio y la hora final de un dia para buscar todos los eventos en X dia
		LocalDateTime inicio = DateUtil.createFechaConHora(reservaContainer.getFecha(), DateUtil.START_TIME);
		LocalDateTime fin = DateUtil.createFechaConHora(reservaContainer.getFecha(), DateUtil.END_TIME);

		// lista de todos los eventos
		List<Evento> listaEvento = eventoService.findAllByStartdBetweenAndSalon(inicio, fin, salon);
		
		// En caso de no existir ningun evento para esa fecha devolver disponible
		if(!listaEvento.isEmpty()) {

			// lista de todas las lista de rangos horarios
			List<List<Integer>> listaDeRangos = new ArrayList<List<Integer>>();
			
			// Variable usada para obtener la hora final del evento
			String horaFinal = null;
			
			// Obtiene el rango horario de los eventos agendados
			for(Evento evento : listaEvento) {
				
				if(evento.getStartd().plusDays(1).getDayOfMonth() == evento.getEndd().getDayOfMonth()) {
					horaFinal = suma24Horas(evento.getEndd());
				}else {
					horaFinal = DateUtil.getHora(evento.getEndd());
				}
				
				listaDeRangos.add(getRango(DateUtil.getHora(evento.getStartd()), horaFinal));
			}

			// Obtiene el rango horario del nuevo evento a agendar
			if(reservaContainer.getResto24()) {
				horaFinal = suma24Horas(DateUtil.createFechaConHora(reservaContainer.getFin()));
			}else {
				horaFinal = reservaContainer.getFin();
			}
			
			List<Integer> rangoEventoNuevo = getRangoConMargen(reservaContainer.getInicio(), horaFinal);

			// Si contiene a alguna fecha devuelve false
			for(List<Integer> rangos : listaDeRangos) {
				if(CollectionUtils.containsAny(rangos, rangoEventoNuevo)) {
					return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
				}
			}
		}
		// Si no intercepta a ninguna da el disponible
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);	
	}
	
	private List<Integer> getRango(String inicio, String fin) {
		String[] horaInicioSplit = inicio.split(":");
		String[] horaFinSplit =  fin.split(":");
		
		int horaInicio = Integer.parseInt(horaInicioSplit[0] + horaInicioSplit[1]);
		int horaFin = Integer.parseInt(horaFinSplit[0] + horaFinSplit[1]);

		List<Integer> range = IntStream.range(horaInicio, horaFin).boxed().collect(Collectors.toList());
		
		return range;
	}
	
	private List<Integer> getRangoConMargen(String inicio, String fin) {
		String[] horaInicioSplit = inicio.split(":");
		String[] horaFinSplit =  fin.split(":");
		
		int horaInicio = Integer.parseInt(horaInicioSplit[0] + horaInicioSplit[1]);
		int horaFin = Integer.parseInt(horaFinSplit[0] + horaFinSplit[1]);

		// Le agrega una hora antes y una hora despues para tener margen
		horaInicio -= 100;
		horaFin += 100;
		
		List<Integer> range = IntStream.range(horaInicio, horaFin).boxed().collect(Collectors.toList());
		
		return range;
	}


	
	private String suma24Horas(LocalDateTime fecha) {
		
		String[] horaFinSplit =  DateUtil.getHora(fecha).split(":");
		
		int finHoraEventos = Integer.parseInt(horaFinSplit[0]) + 24;
		 
		horaFinSplit[0] = Integer.toString(finHoraEventos);

		return horaFinSplit[0] + ":" + horaFinSplit[1];
	}

	
	@GetMapping("/listaEventosByDia")
	public @ResponseBody ResponseEntity<List<String>> listaEventosByDia(Model model, HttpSession session, ReservaContainer reservaContainer){
		
		Salon salon =  (Salon) session.getAttribute(GeneralPath.SALON);

		LocalDateTime inicio = DateUtil.createFechaConHora(reservaContainer.getFecha(), DateUtil.START_TIME);
		LocalDateTime fin = DateUtil.createFechaConHora(reservaContainer.getFecha(), DateUtil.END_TIME);

		List<Evento> listaEvento = eventoService.findAllByStartdBetweenAndSalon(inicio, fin, salon);
		
		List<String> listaFecha = new ArrayList<String>();

		if(!listaEvento.isEmpty()) {
			for(Evento evento : listaEvento) {
				StringBuilder fecha = new StringBuilder();
				// En caso de que sea el dia siguiente le agrega la fecha tambien no solo la hora
				if(evento.getStartd().plusDays(1).getDayOfMonth() == evento.getEndd().getDayOfMonth()) {
					fecha.append(DateUtil.getHora(evento.getStartd()) + " hasta " + DateUtil.getHora(evento.getEndd()) + " del dia " + DateUtil.getFecha(evento.getEndd()));
				}else {
					fecha.append(DateUtil.getHora(evento.getStartd()) + " hasta " + DateUtil.getHora(evento.getEndd()));
				}
				fecha.append(" (" + evento.getSubTipoEvento().getNombre() + ")");
				
				listaFecha.add(fecha.toString());
				
				// Ordena la lista de mas temprano a mas tarde
				Collections.sort(listaFecha);
			}
			return new ResponseEntity<List<String>>(listaFecha, HttpStatus.OK);
		}
		return new ResponseEntity<List<String>>(listaFecha, HttpStatus.CONFLICT);
	}

}
