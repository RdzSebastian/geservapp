package com.estonianport.geservapp.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.CodigoContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SalonService;

@Controller
public class MainController {

	@Autowired
	SalonService salonService;
	
	@Autowired
	EventoService eventoService;

	@RequestMapping("/")
	public String index(Model model) {
		List<Salon> listaSalones = salonService.getAll();
		model.addAttribute("listaSalones", listaSalones);
		return "index";
	}
	
	@RequestMapping("/administracion")
	public String adm(Model model, HttpSession session) {
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		return "adm/adm";
	}
	
	@RequestMapping("/download/0")
	public String descargarComprobante(Model model){
		model.addAttribute("codigoContainer", new CodigoContainer());
		return "download/download";		
	}

	@RequestMapping("/download")
	public ResponseEntity<FileSystemResource> downloadStuff(HttpServletResponse response, CodigoContainer codigoContainer) throws IOException {

		String DIRECTORY_PDF = "pdf/";
		String EXTENSION_PDF = ".pdf";
		
		File file = new File(DIRECTORY_PDF + codigoContainer.getCodigo() + EXTENSION_PDF);
		if (file.exists()) {

		    HttpHeaders respHeaders = new HttpHeaders();
		    respHeaders.setContentType(MediaType.APPLICATION_PDF);
		    respHeaders.setContentLength(file.length());
		    respHeaders.setContentDispositionFormData("attachment", file.getName());

		    return new ResponseEntity<FileSystemResource>(new FileSystemResource(file), respHeaders, HttpStatus.OK);
		}
		return null;
	}

	
	@RequestMapping("/buscarEvento")
	public String buscarEvento(Model model, HttpSession session){
		
		// Agrega el titulo de la busqueda que sea
		model.addAttribute("titulo", session.getAttribute("titulo"));
		
		model.addAttribute("codigoContainer", new CodigoContainer());
		model.addAttribute("volver", session.getAttribute("volver"));
		return "evento/buscarEvento";
	}
	
	@RequestMapping("/seleccionarFecha")
	public String buscarEvento(Model model, @RequestParam("arr") String fecha, HttpSession session){
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start_date = LocalDateTime.parse(fecha + " 10:00", formatter);
		LocalDateTime end_date = LocalDateTime.parse(fecha + " 23:00", formatter);

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
		
		// Agrega el volver de donde venga
		//model.addAttribute("volver", session.getAttribute("volver"));
		
		session.setAttribute("volver", "/eventoEncontrado");
		
		List<Evento> listaEvento = eventoService.getAll();
		model.addAttribute("listaEvento", listaEvento);

		return "seleccionarFecha/abmSeleccionarFecha";
	}
	
	@RequestMapping("/eventoEncontrado")
	public String eventoEncontrado(Model model, HttpSession session, CodigoContainer codigoContainer){
		List<Evento> listaEvento = new ArrayList<Evento>();
		listaEvento.add( eventoService.getEventoByCodigo(codigoContainer.getCodigo()));
		model.addAttribute("listaEvento",listaEvento);
		return "seleccionarFecha/abmSeleccionarFecha";
	}

}
