package com.estonianport.geservapp.controller;

import java.io.File;
import java.io.IOException;
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

import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.CodigoContainer;
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
		model.addAttribute("codigoContainer", new CodigoContainer());
		model.addAttribute("volver", session.getAttribute("volver"));
		return "evento/buscarEvento";
		
	}

}
