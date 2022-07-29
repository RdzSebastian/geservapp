package com.estonianport.geservapp.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import com.estonianport.geservapp.service.ExtraVariableCateringService;
import com.estonianport.geservapp.service.ExtraSubTipoEventoService;
import com.estonianport.geservapp.service.ExtraVariableSubTipoEventoService;
import com.estonianport.geservapp.service.PagoService;
import com.estonianport.geservapp.service.RolService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.ServicioService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoCateringService;
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
	ExtraSubTipoEventoService extraSubTipoEventoService;

	@Autowired
	ExtraVariableSubTipoEventoService extraVariableSubTipoEventoService;

	@Autowired
	ExtraVariableCateringService extraCateringService;

	@Autowired
	TipoCateringService tipoCateringService;

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

		// Trae los eventos por el salon que este preguntando
		List<Evento> listaEvento =  eventoService.getEventosBySalon(salon);

		// Setea la cantidad de todo para la vista de administracion
		model.addAttribute("cantEvento", listaEvento.size());
		model.addAttribute("cantUsuario", usuariosService.count());
		model.addAttribute("cantTipoEvento", tipoEventoService.count());
		model.addAttribute("cantSubTipoEvento", subTipoEventoService.count());
		model.addAttribute("cantPago", pagoService.count());
		model.addAttribute("cantSalon", salonService.count());
		model.addAttribute("cantExtra", extraSubTipoEventoService.count() + extraVariableSubTipoEventoService.count());
		model.addAttribute("cantCatering", tipoCateringService.count() + extraCateringService.count());
		model.addAttribute("cantCliente", clienteService.count());
		model.addAttribute("cantServicio", servicioService.count());

		// Setea si es administrador o rol user
		model.addAttribute("admin", usuarioService.findUserByUsername(authentication.getName()).getRol() == rolService.getRolByNombre("ADMIN"));

		// Descarga compronbante
		session.setAttribute("eventoEncontrado", null);
		
		return GeneralPath.ADM + GeneralPath.PATH_SEPARATOR + GeneralPath.ADM;
	}

	@RequestMapping("/extra")
	public String extraAdm(Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Setea la cantidad de todo para la vista de administracion
		model.addAttribute("cantExtraSubTipoEvento", extraSubTipoEventoService.count());
		model.addAttribute("cantExtraVariableSubTipoEvento", extraVariableSubTipoEventoService.count());

		return GeneralPath.EXTRA + GeneralPath.PATH_SEPARATOR + GeneralPath.EXTRA;
	}

	@RequestMapping("/catering")
	public String cateringAdm(Model model, HttpSession session) {

		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);

		// Setea la cantidad de todo para la vista de administracion
		model.addAttribute("cantTipoCatering", tipoCateringService.count());
		model.addAttribute("cantExtraCatering", extraCateringService.count());

		return GeneralPath.CATERING + GeneralPath.PATH_SEPARATOR + GeneralPath.CATERING;
	}

	@RequestMapping("/download/0")
	public String descargarComprobante(Model model, HttpSession session){
		model.addAttribute("codigoContainer", new CodigoContainer());

		// Trae valor de eventoNoEncontrado en caso de venir de /download y luego lo limpia
		model.addAttribute("eventoEncontrado", session.getAttribute("eventoEncontrado"));
		session.setAttribute("eventoEncontrado", null);
		session.setAttribute(GeneralPath.VOLVER, "/download/0");

		// Agrega el volver de donde venga y action 
		model.addAttribute(GeneralPath.TITULO, "Descargar comprobante");
		model.addAttribute(GeneralPath.ACTION, "/download");
		model.addAttribute(GeneralPath.VOLVER, "/administracion");

		return GeneralPath.EVENTO + "/buscarEvento";
	}

	@RequestMapping("/buscarEvento")
	public String buscarEvento(Model model, HttpSession session){

		model.addAttribute("codigoContainer", new CodigoContainer());

		// Agrega el titulo de la busqueda que sea
		model.addAttribute(GeneralPath.TITULO, session.getAttribute(GeneralPath.TITULO));
		model.addAttribute(GeneralPath.ACTION, session.getAttribute(GeneralPath.ACTION));
		model.addAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));
		
		session.setAttribute(GeneralPath.VOLVER, session.getAttribute(GeneralPath.VOLVER));

		return GeneralPath.EVENTO + "/buscarEvento";
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

		model.addAttribute("listaEvento", eventoService.getEventosBySalon(salon));
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
			model.addAttribute("listaEvento", listaEvento);
			return "seleccionarFecha/abmSeleccionarFecha";
		}

		// Setea el valor de no encontrado
		model.addAttribute("eventoEncontrado", false);

		return GeneralPath.EVENTO + "/buscarEvento";
	}
	
	@RequestMapping("/eventoVolver")
	public String eventoVolver(Model model, HttpSession session){
		List<Evento> listaEvento = new ArrayList<Evento>();
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		
		model.addAttribute(GeneralPath.TITULO, session.getAttribute(GeneralPath.TITULO));
		model.addAttribute(GeneralPath.VOLVER, GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_EVENTO + GeneralPath.PATH_SEPARATOR + salon.getId());

		listaEvento.add(eventoService.getEventoByCodigo((String) session.getAttribute("codigoEvento")));
		model.addAttribute("listaEvento", listaEvento);
		return "seleccionarFecha/abmSeleccionarFecha";
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<FileSystemResource> download(Model model, CodigoContainer codigoContainer, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException{

		if(eventoService.existsByCodigo(codigoContainer.getCodigo())) {
			// Busca el evento por codigo y si no existe no devuelve nada
			Evento evento = eventoService.getEventoByCodigo(codigoContainer.getCodigo());
			if(evento != null) {
				try {

					ServletContext adminContext = request.getServletContext();
					ServletContext uploadsContext = adminContext.getContext(GeneralPath.PATH_SEPARATOR + GeneralPath.DIRECTORY_PDF);
					String absolutePath = uploadsContext.getRealPath("");

					File fileDirectory = new File(absolutePath + GeneralPath.DIRECTORY_PDF);

					// Crea el directorio si no existe
					if(!fileDirectory.exists()){
						fileDirectory.mkdir();
					}

					// Crea el archivo si no existe
					File fileDirectoryFile = new File(absolutePath + GeneralPath.DIRECTORY_PDF + codigoContainer.getCodigo() + GeneralPath.EXTENSION_PDF);
					if(!fileDirectoryFile.exists()) {
						ReservaContainer reservaContainer = new ReservaContainer();
						reservaContainer.setEvento(evento);

						itextService.createPdf(reservaContainer, fileDirectoryFile.getAbsolutePath());
					}

					// Prepara archivo para descarga
					HttpHeaders respHeaders = new HttpHeaders();
					respHeaders.setContentType(MediaType.APPLICATION_PDF);
					respHeaders.setContentLength(fileDirectoryFile.length());
					respHeaders.setContentDispositionFormData("attachment", fileDirectoryFile.getName());

					// Setea el valor de encontrado
					session.setAttribute("eventoEncontrado", true);
					
					// TODO hacer que al devolver correcto saque el cartel de error y cargue el de ok
					//response.sendRedirect((String) session.getAttribute(GeneralPath.VOLVER));

					return new ResponseEntity<FileSystemResource>(new FileSystemResource(fileDirectoryFile), respHeaders, HttpStatus.OK);
				} catch (Exception e) {

					// Setea el valor de no encontrado
					session.setAttribute("eventoEncontrado", false);

					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
		}

		// Setea el valor de no encontrado
		session.setAttribute("eventoEncontrado", false);

		// Hace que al devolver not found se quede en la pantalla buscar
		response.sendRedirect((String) session.getAttribute(GeneralPath.VOLVER));

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
