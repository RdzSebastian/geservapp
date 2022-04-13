
package com.estonianport.geservapp.commons;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;

@Service
public class EmailService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender sender;

	public void sendEmail(Email emailBody)  {
		sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject());
	}

	private void sendEmailTool(String textMessage, String email, String subject) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(email);
			helper.setText(textMessage, true);
			helper.setSubject(subject);
			sender.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Hubo un error al enviar el mail: ", e);
		}
	}

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

		String dia = evento.getStart_date().getDayOfMonth() + "-" + evento.getStart_date().getMonth().getValue() + "-" + evento.getStart_date().getYear();
		String horaInicio = String.valueOf(evento.getStart_date().getHour()) + ":" +String.valueOf(evento.getStart_date().getMinute());
		String horaFin = String.valueOf(evento.getEnd_date().getHour()) + ":" +String.valueOf(evento.getEnd_date().getMinute());

		Email emailBody = new Email();
		emailBody.setEmail("rdzsebastian@gmail.com");
		emailBody.setSubject("Tu reserva de evento para el " + evento.getStart_date());
		emailBody.setContent(
				"Tu evento: " + evento.getNombre() + ", ha sido reservado exitosamente." + "<br>" +
				"Codigo: " + evento.getCodigo() + "<br>" +
				"Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
				"En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
				"Contrataste un evento " + evento.getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
				extraMail + "<br>" +
				"<br>" +
				"El precio final del evento es: " + evento.getPresupuesto());

		this.sendEmail(emailBody);
	}

}