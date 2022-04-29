
package com.estonianport.geservapp.commons;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.Pago;

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

	public void enviarMailComprabanteReserva(ReservaContainer reservaContainer) {

		StringBuilder extraMail = new StringBuilder();
		Set<Extra> listaExtra = reservaContainer.getExtra();
		Evento evento = reservaContainer.getEvento();

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

		String dia = evento.getStartd().getDayOfMonth() + "-" + evento.getStartd().getMonth().getValue() + "-" + evento.getStartd().getYear();
		String horaInicio = String.valueOf(evento.getStartd().getHour()) + ":" +String.valueOf(evento.getStartd().getMinute());
		String horaFin = String.valueOf(evento.getEndd().getHour()) + ":" +String.valueOf(evento.getEndd().getMinute());

		Email emailBody = new Email();
		emailBody.setEmail("rdzsebastian@gmail.com");
		emailBody.setSubject("Tu evento: " +  evento.getNombre() + " para el " + dia + ", codigo: " + evento.getCodigo());
		emailBody.setContent(
				"Tu evento: " + evento.getNombre() + ", ha sido reservado exitosamente." + "<br>" +
						"Codigo: " + evento.getCodigo() + "<br>" +
						"Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
						"En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
						"Contrataste un evento " + evento.getSubTipoEvento().getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
						extraMail + "<br>" +
						"<br>" +
						"El precio final del evento es: " + evento.getPresupuesto());

		this.sendEmail(emailBody);
	}


	public void enviarMailComprabantePago(Pago pago, List<Pago> listaPagos) {

		Evento evento = pago.getEvento();

		String diaPago = pago.getFecha().getDayOfMonth() + "-" + pago.getFecha().getMonth().getValue() + "-" + pago.getFecha().getYear();
		String horaPago = String.valueOf(pago.getFecha().getHour()) + ":" +String.valueOf(pago.getFecha().getMinute());

		String dia = evento.getStartd().getDayOfMonth() + "-" + evento.getStartd().getMonth().getValue() + "-" + evento.getStartd().getYear();
		String horaInicio = String.valueOf(evento.getStartd().getHour()) + ":" +String.valueOf(evento.getStartd().getMinute());
		String horaFin = String.valueOf(evento.getEndd().getHour()) + ":" +String.valueOf(evento.getEndd().getMinute());

		int totalPago = 0;
		for(Pago pagos :listaPagos) {
			totalPago += pagos.getPago();
		}

		Email emailBody = new Email();
		emailBody.setEmail("rdzsebastian@gmail.com");
		emailBody.setSubject("Tu pago del evento  " + evento.getNombre() + ", codigo: " + evento.getCodigo());
		emailBody.setContent(
				"Tu pago para el evento: " + evento.getNombre() + " ha sido realizado exitosamente." + "<br>" +
						"Fecha de pago: " + diaPago + " hora: " + horaPago + "<br>" +
						"Monto abonado: $" + pago.getPago() + "<br>" +
						"Monto total abonado hasta la fecha: $" + totalPago  + "<br>" +
						"Monto faltante: $" + Math.abs(evento.getPresupuesto() - totalPago)  + "<br>" +
						"El precio total del evento: $" + evento.getPresupuesto() + "<br>" +
						"Acercate cuando quieras al salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
						"Para terminar de abonarlo." + "<br>" +
						"Te recordamos que tu evento se realizara el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>");

		this.sendEmail(emailBody);
	}

}