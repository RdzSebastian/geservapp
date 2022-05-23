package com.estonianport.geservapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import  com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.SexoService;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private SexoService sexoService;

	@RequestMapping("/abmCliente")
	public String abm(Model model, HttpSession session) {
		model.addAttribute("listaCliente", clienteService.getAll());
		
		// Salon en sesion para volver al calendario
		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		model.addAttribute(GeneralPath.SALON, salon);
		
		return GeneralPath.CLIENTE + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_CLIENTE;
	}

	@GetMapping("/saveCliente/{id}")
	public String showCliente(@PathVariable("id") Long id, Model model) {
		// Agrega lista Sexo
		model.addAttribute("listaSexo", sexoService.getAll());

		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.CLIENTE, clienteService.get(id));
		}else {
			model.addAttribute(GeneralPath.CLIENTE, new Cliente());
		}
		return GeneralPath.CLIENTE + GeneralPath.PATH_SEPARATOR + GeneralPath.SAVE_CLIENTE;
	}

	@PostMapping("/saveCliente")
	public String save(Cliente cliente, Model model) {
		clienteService.save(cliente);
		return GeneralPath.REDIRECT + GeneralPath.ABM_CLIENTE;
	}

	@GetMapping("/deleteCliente/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		clienteService.delete(id);
		return GeneralPath.REDIRECT + GeneralPath.ABM_CLIENTE;
	}
}
