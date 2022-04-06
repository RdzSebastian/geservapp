package com.estonianport.geservapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventoDao;
import com.estonianport.geservapp.email.EmailBody;
import com.estonianport.geservapp.email.EmailService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Salon;
import com.estonianport.geservapp.service.EventoService;

@Service
public class EventoServiceImpl extends GenericServiceImpl<Evento, Long> implements EventoService{

	@Autowired
	private EventoDao eventosDao;
	
	@Autowired
	private EmailService emailService;

	@Override
	public CrudRepository<Evento, Long> getDao() {
		return eventosDao;
	}

	public List<Evento> getEventosBySalon(Salon salon){
		return eventosDao.getEventosBySalon(salon);
	}

	@Override
	public void enviarMailComprabanteReserva(Evento evento, List<Extra> listaExtra) {
		
		StringBuilder extraMail = new StringBuilder();

		if(listaExtra != null && !listaExtra.isEmpty()) {
			int i = 0;
			extraMail.append("Con los siguientes extras ");
			for(Extra extra : listaExtra) {
				extraMail.append(extra.getNombre());
				i++;
				if(i < listaExtra.size()) {
					extraMail.append(", ");
				}else {
					extraMail.append(".");
				}
			}
		}else {
			extraMail.append("Sin ningun extra.");
		}

		
		EmailBody emailBody = new EmailBody();
		emailBody.setEmail("rdzsebastian@gmail.com");
		emailBody.setSubject("Tu reserva de evento para el " + evento.getStart_date());
		emailBody.setContent(
				"Tu evento: " + evento.getNombre() + ", ha sido reservado exitosamente." + "<br>" +
				"Te esperamos el dia " + evento.getStart_date() + " de " + "hora inicio" + " a " + "hora final" + "." + "<br>" +
				"En el salon: " + evento.getSalon().getNombre() + " en calle " + "direcion" + ", " + "municipio" + "." + "<br>" +
				"Contrataste un evento " + evento.getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
				extraMail + "<br>" +
				"<br>" +
				"El precio final del evento es: " + "precio");

		emailService.sendEmail(emailBody);
	}

}
