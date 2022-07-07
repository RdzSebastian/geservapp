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
import com.estonianport.geservapp.commons.GeneralPath;
import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.json.FullCalendarJSON;
import com.estonianport.geservapp.model.Cliente;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.PrecioConFecha;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.model.SubTipoEvento;
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

	private String suma24Horas(LocalDateTime fecha) {

		String[] horaFinSplit =  DateUtil.getHora(fecha).split(":");

		int finHoraEventos = Integer.parseInt(horaFinSplit[0]) + 24;

		horaFinSplit[0] = Integer.toString(finHoraEventos);

		return horaFinSplit[0] + ":" + horaFinSplit[1];
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
		return new ResponseEntity<List<String>>(listaFecha, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")	
	@GetMapping("/precioEventoBySubTipoEventoYFecha")
	public @ResponseBody ResponseEntity<Integer> precioEventoBySubTipoEventoYFecha(Model model, @RequestParam(value="fecha") String fecha, @RequestParam(value="subTipoEventoId") long subTipoEventoId, HttpSession session){

		Salon salon = (Salon) session.getAttribute(GeneralPath.SALON);
		SubTipoEvento subTipoEvento = subTipoEventoService.get(subTipoEventoId);
		LocalDateTime fechaEvento = DateUtil.createFechaConHora(fecha, DateUtil.START_TIME);

		List<PrecioConFecha> listaPrecioConFecha = subTipoEvento.getListaPrecioConFecha();

		for(PrecioConFecha precioConFecha : listaPrecioConFecha) {

			if(fechaEvento.getYear() == precioConFecha.getDesde().getYear()) {
				List<Integer> rangoMeses = IntStream.range(precioConFecha.getDesde().getMonthValue(), precioConFecha.getHasta().getMonthValue() + 1).boxed().collect(Collectors.toList());

				if(rangoMeses.contains(fechaEvento.getMonthValue()) && precioConFecha.getSalon().getId() == salon.getId()){

					if(DateUtil.isFinDeSemana(fechaEvento)) {
						return new ResponseEntity<Integer>( + subTipoEvento.getValorFinSemana(), HttpStatus.OK);
					}else {
						return new ResponseEntity<Integer>(precioConFecha.getPrecio(), HttpStatus.OK);
					}
				}
			}
		}
		return new ResponseEntity<Integer>(0, HttpStatus.OK);
	}


}
