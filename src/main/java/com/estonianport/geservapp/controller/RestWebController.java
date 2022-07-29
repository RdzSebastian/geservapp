package com.estonianport.geservapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.estonianport.geservapp.commons.DateUtil;
import com.estonianport.geservapp.commons.EmailService;
import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.ExtraCapacidadContainer;
import com.estonianport.geservapp.container.ExtraContainer;
import com.estonianport.geservapp.container.FechaHoraInicioSubTipoEventoContainer;
import com.estonianport.geservapp.container.ListaExtrasContainer;
import com.estonianport.geservapp.container.OtroDiaHoraFinContainer;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.json.FullCalendarJSON;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.PrecioConFechaSubTipoEvento;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
import com.estonianport.geservapp.model.TipoCatering;
import com.estonianport.geservapp.service.ClienteService;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/eventos")
public class RestWebController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;

	/**
	 * acquires Event information to be displayed on the calendar
	 * @return Event information json encoded string
	 */

	@GetMapping(value = "/all")
	public String getEventos(HttpSession session) {
		String jsonMsg = null;
		try {

			List<FullCalendarJSON> eventos = new ArrayList<FullCalendarJSON>();
			FullCalendarJSON evento = null;

			Salon salon = (Salon) session.getAttribute("salon");
			List<Evento> eventosDDBB = eventoService.getEventosBySalon(salon);

			for (Evento eventoDDBB : eventosDDBB) {
				evento = new FullCalendarJSON();

				evento.setId(eventoDDBB.getId());
				evento.setTitle(eventoDDBB.getNombre());

				if(eventoDDBB.getStartd() != null) {
					evento.setStart(eventoDDBB.getStartd().toString());
				}

				if(eventoDDBB.getEndd() != null) {
					evento.setEnd(eventoDDBB.getEndd().toString());	
				}
				eventos.add(evento);
			}

			//FullCalendar pass encoded string 
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventos);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/buscarClientePorCuil")
	public @ResponseBody ResponseEntity<Cliente> buscarClientePorCuil(Model model, @RequestParam(value="cuil") long cuil){

		if(clienteService.existsByCuil(cuil) && cuil != 0) {
			Cliente cliente = clienteService.getClienteByCuil(cuil);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		return new ResponseEntity<Cliente>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
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

				if(evento.getStartd().plusDays(1).getDayOfYear() == evento.getEndd().getDayOfYear()) {
					horaFinal = suma24Horas(evento.getEndd());
				}else {
					horaFinal = DateUtil.getHorario(evento.getEndd());
				}

				listaDeRangos.add(getRango(DateUtil.getHorario(evento.getStartd()), horaFinal));
			}

			// Obtiene el rango horario del nuevo evento a agendar
			if(reservaContainer.getResto24()) {
				horaFinal = suma24Horas(DateUtil.createFechaConHora(reservaContainer.getFecha(), reservaContainer.getFin()));
			}else {
				horaFinal = reservaContainer.getFin();
			}

			List<Integer> rangoEventoNuevo = getRangoConMargen(reservaContainer.getInicio(), horaFinal);

			// Si contiene a alguna fecha devuelve false
			for(List<Integer> rangos : listaDeRangos) {
				if(CollectionUtils.containsAny(rangos, rangoEventoNuevo)) {
					return new ResponseEntity<Boolean>(false, HttpStatus.OK);
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

	private String suma24Horas(LocalDateTime fechaFin) {

		String horaFin =  DateUtil.getHora(fechaFin);
		String minutosFin =  DateUtil.getMinutos(fechaFin);

		int finHoraEventos = Integer.parseInt(horaFin) + 24;

		horaFin = Integer.toString(finHoraEventos);

		return horaFin + ":" + minutosFin;
	}

	@CrossOrigin(origins = "*")
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
					fecha.append(DateUtil.getHorario(evento.getStartd()) + " hasta " + DateUtil.getHorario(evento.getEndd()) + " del dia " + DateUtil.getFecha(evento.getEndd()));
				}else {
					fecha.append(DateUtil.getHorario(evento.getStartd()) + " hasta " + DateUtil.getHorario(evento.getEndd()));
				}
				fecha.append(" (" + evento.getSubTipoEvento().getNombre() + ")");

				listaFecha.add(fecha.toString());

				// Ordena la lista de mas temprano a mas tarde
				Collections.sort(listaFecha);
			}
			return new ResponseEntity<List<String>>(listaFecha, HttpStatus.OK);
		}
		return new ResponseEntity<List<String>>(listaFecha, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/precioEventoBySubTipoEventoYFecha")
	public @ResponseBody ResponseEntity<Integer> precioEventoBySubTipoEventoYFecha(Model model, @RequestParam(value="fecha") String fecha, @RequestParam(value="subTipoEventoId") long subTipoEventoId, HttpSession session){

		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		SubTipoEvento subTipoEvento = subTipoEventoService.get(subTipoEventoId);
		LocalDateTime fechaEvento = DateUtil.createFechaConHora(fecha, DateUtil.START_TIME);

		List<PrecioConFechaSubTipoEvento> listaPrecioConFecha = subTipoEvento.getListaPrecioConFecha();

		for(PrecioConFecha precioConFecha : listaPrecioConFecha) {

			if(fechaEvento.getYear() == precioConFecha.getDesde().getYear()) {
				List<Integer> rangoMeses = IntStream.range(precioConFecha.getDesde().getMonthValue(), precioConFecha.getHasta().getMonthValue() + 1).boxed().collect(Collectors.toList());

				if(rangoMeses.contains(fechaEvento.getMonthValue()) && precioConFecha.getSalon().getId() == salon.getId()){

					if(DateUtil.isFinDeSemana(fechaEvento)) {
						return new ResponseEntity<Integer>(precioConFecha.getPrecio() + subTipoEvento.getValorFinSemana(), HttpStatus.OK);
					}else {
						return new ResponseEntity<Integer>(precioConFecha.getPrecio(), HttpStatus.OK);
					}
				}
			}
		}
		return new ResponseEntity<Integer>(0, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/setTimeEndBySubTipoEvento")
	public @ResponseBody ResponseEntity<OtroDiaHoraFinContainer> setTimeEndBySubTipoEvento(Model model, FechaHoraInicioSubTipoEventoContainer horaInicioFinSubTipoEventoContainer){

		OtroDiaHoraFinContainer otroDiaHoraFinContainer = new OtroDiaHoraFinContainer();

		// Obtiene el subTipoEvento para buscar la duracion y crea la fecha inicio y fin
		SubTipoEvento subTipoEvento = subTipoEventoService.get((long) horaInicioFinSubTipoEventoContainer.getSubTipoEventoId());
		LocalDateTime fechaEventoInicio = DateUtil.createFechaConHora(horaInicioFinSubTipoEventoContainer.getFecha(), horaInicioFinSubTipoEventoContainer.getInicio());
		LocalDateTime fechaEventoFin = DateUtil.createFechaConHora(horaInicioFinSubTipoEventoContainer.getFecha(), horaInicioFinSubTipoEventoContainer.getInicio());

		// Calcula la hora y fecha final
		fechaEventoFin = fechaEventoFin.plusHours(subTipoEvento.getDuracion().getHour());
		fechaEventoFin = fechaEventoFin.plusMinutes(subTipoEvento.getDuracion().getMinute());

		// Setea la hora y fecha final
		otroDiaHoraFinContainer.setFin_hora(DateUtil.getHora(fechaEventoFin));
		otroDiaHoraFinContainer.setFin_minutos(DateUtil.getMinutos(fechaEventoFin));

		// Setea si la hora es del dia siguiente
		otroDiaHoraFinContainer.setOtroDia(fechaEventoInicio.getDayOfYear() != fechaEventoFin.getDayOfYear());

		return new ResponseEntity<OtroDiaHoraFinContainer>(otroDiaHoraFinContainer, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/setExtraAdultosYNinoCapacidad")
	public @ResponseBody ResponseEntity<ExtraCapacidadContainer> setExtraAdultosYNinoCapacidad(Model model,  @RequestParam(value="adultos") int adultos, @RequestParam(value="ninos") int ninos, @RequestParam(value="subTipoEventoId") long subTipoEventoId){

		// Obtiene el subTipoEvento para buscar la duracion y crea la fecha inicio y fin
		SubTipoEvento subTipoEvento = subTipoEventoService.get(subTipoEventoId);

		// Por cada 20 adultos extra se suma una camarera
		int cantidadExtraAdultos = adultos - subTipoEvento.getCapacidad().getCapacidadAdultos();
		int cantidadCamarerasExtra = 0;

		// Si la cantidad de adultos es mayor a 0 ya se incluye 1 camarera de ahi por cada 20 mas se suma otra
		if(cantidadExtraAdultos > 0) {
			cantidadCamarerasExtra = ((cantidadExtraAdultos - 1) / 20) + 1;
		}

		// La cantidad de ninos se cobra por nino extra
		int cantidadExtraNinos = ninos - subTipoEvento.getCapacidad().getCapacidadNinos();

		// Si la cantidad de ninos es menor a 0 se setea 0, pero si es mayor se setea la cantidad que sea en el container
		if(cantidadExtraNinos < 0) {
			cantidadExtraNinos = 0;
		}

		ExtraVariableSubTipoEvento extraVariableCamarera = null;
		ExtraVariableSubTipoEvento extraVariableNino = null;
		
		for(ExtraVariableSubTipoEvento extraVariableEvento : subTipoEvento.getListaExtraVariableSubTipoEvento()){
			if(extraVariableEvento.getNombre().contains("Camarera")) {
				extraVariableCamarera = extraVariableEvento;
			}
			
			if(extraVariableEvento.getNombre().contains("Niños")) {
				extraVariableNino = extraVariableEvento;
			}
			
		}

		// Prepara el container para el envio de datos
		ExtraCapacidadContainer extraCapacidadContainer = new ExtraCapacidadContainer();
		extraCapacidadContainer.setCantidadCamarera(cantidadCamarerasExtra);
		extraCapacidadContainer.setIdExtraCamarera(extraVariableCamarera.getId().intValue());
		extraCapacidadContainer.setCantidadNinos(cantidadExtraNinos);
		extraCapacidadContainer.setIdExtraNinos(extraVariableNino.getId().intValue());

		return new ResponseEntity<ExtraCapacidadContainer>(extraCapacidadContainer, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/reenviarMail")
	public @ResponseBody ResponseEntity<Boolean> reenviarMail(Model model,  @RequestParam(value="codigo") String codigo){

		Evento evento = eventoService.getEventoByCodigo(codigo);

		ReservaContainer reservaContainer = new ReservaContainer();
		reservaContainer.setEvento(evento);

		try {
			emailService.enviarMailComprabanteReserva(reservaContainer, "sido reservado");
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/getExtrasConPrecio")
	public @ResponseBody ResponseEntity<ListaExtrasContainer> getExtrasConPrecio(Model model, HttpSession session, @RequestParam(value="fecha") String fecha, @RequestParam(value="subTipoEventoId") long subTipoEventoId){

		// Obtiene el subTipoEvento
		SubTipoEvento subTipoEvento = subTipoEventoService.get(subTipoEventoId);
		
		Salon salon =  (Salon) session.getAttribute(GeneralPath.SALON);

		LocalDateTime fechaLocalDateTime = DateUtil.createFechaConHora(fecha, DateUtil.START_TIME);

		List<ExtraContainer> listaExtra = new ArrayList<ExtraContainer>();
		List<ExtraContainer> listaExtraVariable = new ArrayList<ExtraContainer>();
		List<ExtraContainer> listaExtraCatering = new ArrayList<ExtraContainer>();
		List<ExtraContainer> listaTipoCatering = new ArrayList<ExtraContainer>();
		
		// Obtiene la lista de extras
		for(ExtraSubTipoEvento extra : subTipoEvento.getListaExtraSubTipoEvento()) {
			for(PrecioConFecha PrecioConFecha : extra.getListaPrecioConFecha()) {
				ExtraContainer extraContainer = getExtra(fechaLocalDateTime, PrecioConFecha, salon, extra.getId(), extra.getNombre());
				if(extraContainer != null) {
					listaExtra.add(extraContainer);
				}
			}
		}
		
		// Obtiene la lista de extras variables
		for(ExtraVariableSubTipoEvento extra : subTipoEvento.getListaExtraVariableSubTipoEvento()) {
			for(PrecioConFecha PrecioConFecha : extra.getListaPrecioConFecha()) {
				ExtraContainer extraContainer = getExtra(fechaLocalDateTime, PrecioConFecha, salon, extra.getId(), extra.getNombre());
				if(extraContainer != null) {
					listaExtraVariable.add(extraContainer);
				}
			}
		}
		
		// Obtiene la lista de extras catering
		for(ExtraVariableCatering extra : subTipoEvento.getListaExtraVariableCatering()) {
			for(PrecioConFecha PrecioConFecha : extra.getListaPrecioConFecha()) {
				ExtraContainer extraContainer = getExtra(fechaLocalDateTime, PrecioConFecha, salon, extra.getId(), extra.getNombre());
				if(extraContainer != null) {
					listaExtraCatering.add(extraContainer);
				}
			}
		}

		// Obtiene la lista de tipo catering
		for(TipoCatering extra : subTipoEvento.getListaTipoCatering()) {
			for(PrecioConFecha PrecioConFecha : extra.getListaPrecioConFecha()) {
				ExtraContainer extraContainer = getExtra(fechaLocalDateTime, PrecioConFecha, salon, extra.getId(), extra.getNombre());
				if(extraContainer != null) {
					listaTipoCatering.add(extraContainer);
				}
			}
		}
		
		ListaExtrasContainer listaExtrasContainer = new ListaExtrasContainer();
		listaExtrasContainer.setListaExtra(listaExtra);
		listaExtrasContainer.setListaExtraVariable(listaExtraVariable);
		listaExtrasContainer.setListaExtraCatering(listaExtraCatering);
		listaExtrasContainer.setListaTipoCatering(listaTipoCatering);

		return new ResponseEntity<ListaExtrasContainer>(listaExtrasContainer, HttpStatus.OK);
	}



	private ExtraContainer getExtra(LocalDateTime fecha, PrecioConFecha precioConFecha, Salon salon, Long extraId, String extraNombre) {
	
		if(fecha.getYear() == precioConFecha.getDesde().getYear()) {
			List<Integer> rangoMeses = IntStream.range(precioConFecha.getDesde().getMonthValue(), precioConFecha.getHasta().getMonthValue() + 1).boxed().collect(Collectors.toList());

			if(rangoMeses.contains(fecha.getMonthValue()) && precioConFecha.getSalon().getId() == salon.getId()){
				return new ExtraContainer(extraId, extraNombre, precioConFecha.getPrecio());
			}
		}
		return null;
	}


}
