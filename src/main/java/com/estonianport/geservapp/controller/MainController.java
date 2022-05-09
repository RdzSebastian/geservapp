package com.estonianport.geservapp.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.commons.ItextService;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.container.ReservaContainer;
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
		model.addAttribute("titulo", "Descargar comprobante");
		model.addAttribute("action", "/download");
		model.addAttribute("volver", "/administracion");

		return "evento/buscarEvento";		
	}


	@RequestMapping("/buscarEvento")
	public String buscarEvento(Model model, HttpSession session){

		model.addAttribute("codigoContainer", new CodigoContainer());

		// Agrega el titulo de la busqueda que sea
		model.addAttribute("titulo", session.getAttribute("titulo"));
		model.addAttribute("action", session.getAttribute("action"));
		model.addAttribute("volver", session.getAttribute("volver"));

		return "evento/buscarEvento";
	}

	@RequestMapping("/seleccionarFecha")
	public String buscarEvento(Model model, @RequestParam("arr") String fecha, HttpSession session){

		LocalDateTime start_date = LocalDateTime.parse(fecha + " 10:00", DateUtil.dateTimeFormatter);
		LocalDateTime end_date = LocalDateTime.parse(fecha + " 23:00", DateUtil.dateTimeFormatter);

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Agrega el volver de donde venga
		model.addAttribute("volver", session.getAttribute("volver"));

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
		session.setAttribute("titulo", "Buscar evento");
		session.setAttribute("action", "/eventoEncontrado");
		session.setAttribute("volver", "/buscarAllEvento");

		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);
		model.addAttribute("volver", "/administracion");

		return "seleccionarFecha/abmSeleccionarFecha";
	}

	@RequestMapping("/eventoEncontrado")
	public String eventoEncontrado(Model model, HttpSession session, CodigoContainer codigoContainer){
		List<Evento> listaEvento = new ArrayList<Evento>();
		model.addAttribute("titulo", session.getAttribute("titulo"));
		model.addAttribute("volver", session.getAttribute("volver"));

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

}
