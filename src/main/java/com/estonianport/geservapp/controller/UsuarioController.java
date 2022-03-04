package com.estonianport.geservapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.model.Rol;
import com.estonianport.geservapp.model.Usuario;
import com.estonianport.geservapp.service.RolService;
import com.estonianport.geservapp.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@RequestMapping("/abmUsuario")
	public String abm(Model model) {
		model.addAttribute("listaUsuario", usuarioService.getAll());
		return GeneralPath.USUARIO + GeneralPath.PATH_SEPARATOR + GeneralPath.ABM_USUARIO;
	}
	
	@GetMapping("/saveUsuario/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		List<Rol> listaRoles = rolService.getAll();
		model.addAttribute("listaRoles", listaRoles);
		
		if(id != null && id != 0) {
			model.addAttribute(GeneralPath.USUARIO, usuarioService.get(id));
		}else {
			model.addAttribute(GeneralPath.USUARIO, new Usuario());
		}
		return GeneralPath.USUARIO + GeneralPath.PATH_SEPARATOR + "saveUsuario";
	}
	
	@PostMapping("/saveUsuario")
	public String save(Usuario usuario, Model model) {
		usuarioService.save(usuario);
		return "redirect:/" + GeneralPath.ABM_USUARIO;
	}

	@GetMapping("/deleteUsuario/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		usuarioService.delete(id);
		return "redirect:/" +  GeneralPath.ABM_USUARIO;
	}
}
