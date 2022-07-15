
package com.estonianport.geservapp.commons;

import java.util.HashSet;
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
import com.estonianport.geservapp.model.CateringExtraVariableCatering;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.model.ExtraSubTipoEvento;
import com.estonianport.geservapp.model.ExtraVariableCatering;
import com.estonianport.geservapp.model.ExtraVariableSubTipoEvento;
import com.estonianport.geservapp.model.Pago;
import com.estonianport.geservapp.model.Servicio;
import com.estonianport.geservapp.model.TipoCatering;

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

	public void enviarMailComprabanteReserva(ReservaContainer reservaContainer, String action) {

		Evento evento = reservaContainer.getEvento();

		// -------------------------- Extra --------------------------
		Set<ExtraSubTipoEvento> listaExtra = reservaContainer.getExtraSubTipoEvento();
		StringBuilder extraMail = new StringBuilder();

		if(listaExtra != null && !listaExtra.isEmpty()) {
			int i = 0;
			extraMail.append("Con los siguientes extras: ");
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

		// -------------------------- Extra variable --------------------------
		Set<EventoExtraVariableSubTipoEvento> listaEventoExtraVariable = evento.getListaEventoExtraVariable();

		Set<ExtraVariableSubTipoEvento> listaExtraVariable = new HashSet<ExtraVariableSubTipoEvento>();

		for(EventoExtraVariableSubTipoEvento eventoExtraVariableSubTipoEvento : listaEventoExtraVariable) {
			listaExtraVariable.add(eventoExtraVariableSubTipoEvento.getExtraVariableSubTipoEvento());
		}

		StringBuilder extraVariableMail = new StringBuilder();

		if(listaExtraVariable != null && !listaExtraVariable.isEmpty()) {
			int i = 0;
			extraVariableMail.append("Con los siguientes extras variables: ");
			for(ExtraVariableSubTipoEvento extraVariableSubTipoEvento : listaExtraVariable) {
				extraVariableMail.append(extraVariableSubTipoEvento.getNombre());
				i++;
				if(i < listaExtraVariable.size()) {
					extraVariableMail.append(", ");
				}else {
					extraVariableMail.append(".");
				}
			}
		}else {
			extraVariableMail.append("Sin ningun extra variable.");
		}

		// -------------------------- Servicio --------------------------
		Set<Servicio> listaServicios = evento.getSubTipoEvento().getListaServicio();
		StringBuilder servicioMail = new StringBuilder();

		if(listaServicios != null && !listaServicios.isEmpty()) {
			int i = 0;
			servicioMail.append("El evento incluye los siguientes servicios: ");
			for(Servicio servicio : listaServicios) {
				servicioMail.append(servicio.getNombre());
				i++;
				if(i < listaServicios.size()) {
					servicioMail.append(", ");
				}else {
					servicioMail.append(".");
				}
			}
		}else {
			extraMail.append("El evento no incluye ningun otro servicio.");
		}

		StringBuilder catering = new StringBuilder();

		// -------------------------- Catering --------------------------
		if(evento.getCatering() != null && evento.getCatering().getPresupuesto() != 0) {

			// ------------------- Tipo Catering -----------------------
			Set<TipoCatering> listaTipoCatering = evento.getCatering().getListaTipoCatering();
			StringBuilder tipoCateringMail = new StringBuilder();

			if(listaTipoCatering != null && !listaTipoCatering.isEmpty()) {
				int i = 0;
				tipoCateringMail.append("Tipo de catering: ");
				for(TipoCatering tipoCatering : listaTipoCatering) {
					tipoCateringMail.append(tipoCatering.getNombre());
					i++;
					if(i < listaTipoCatering.size()) {
						tipoCateringMail.append(", ");
					}else {
						tipoCateringMail.append(".");
					}
				}
			}else {
				tipoCateringMail.append("El evento no incluye ningun tipo catering.");
			}

			// ------------------- Extra Catering -----------------------
			Set<CateringExtraVariableCatering> listaCateringExtraCatering = evento.getCatering().getListaCateringExtraVariableCatering();

			Set<ExtraVariableCatering> listaExtraCatering = new HashSet<ExtraVariableCatering>();

			for(CateringExtraVariableCatering cateringExtraVariableCatering : listaCateringExtraCatering) {
				listaExtraCatering.add(cateringExtraVariableCatering.getExtraVariableCatering());
			}

			StringBuilder extraVariableCateringMail = new StringBuilder();

			if(listaExtraCatering != null && !listaExtraCatering.isEmpty()) {
				int i = 0;
				extraVariableCateringMail.append("Extras catering: ");
				for(ExtraVariableCatering extraVariableCatering : listaExtraCatering) {
					extraVariableCateringMail.append(extraVariableCatering.getNombre());
					i++;
					if(i < listaExtraCatering.size()) {
						extraVariableCateringMail.append(", ");
					}else {
						extraVariableCateringMail.append(".");
					}
				}
			}else {
				extraVariableCateringMail.append("El evento no incluye extra catering.");
			}
			
			catering.append("El catering contratado es el siguiente: ");
			catering.append("<br>");
			catering.append(tipoCateringMail);
			catering.append("<br>");
			catering.append(extraVariableCateringMail);
			catering.append("<br>");
		}

		String dia = DateUtil.getFecha(evento.getStartd());
		String horaInicio = DateUtil.getHorario(evento.getStartd());
		String horaFin = DateUtil.getHorario(evento.getEndd());

		Email emailBody = new Email();
		emailBody.setEmail(evento.getCliente().getEmail());
		emailBody.setSubject("Tu evento: " +  evento.getNombre() + " para el " + dia + ", codigo: " + evento.getCodigo());
		emailBody.setContent(
				"Tu evento: " + evento.getNombre() + ", ha " + action + " exitosamente." + "<br>" +
						"Codigo: " + evento.getCodigo() + "<br>" +
						"Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
						"En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
						"Contrataste un evento " + evento.getSubTipoEvento().getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
						"<br>" +
						extraMail + "<br>" +
						"<br>" +
						extraVariableMail + "<br>" +
						"<br>" +
						catering + "<br>" +
						"<br>" +
						"El precio final del evento es: " + evento.getPresupuesto() + "<br>" +
						"<br>" +
						servicioMail + "<br>");

		this.sendEmail(emailBody);
	}


	//	public StringBuilder crearListaExtraParaMail(Set<Extra> listaParaTransformar, String tituloTiene, String tituloNoTiene) {
	//		StringBuilder stringBuilder = new StringBuilder();
	//
	//		if(listaParaTransformar != null && !listaParaTransformar.isEmpty()) {
	//			int i = 0;
	//			stringBuilder.append("El evento incluye los siguientes servicios ");
	//			for(Extra extra : listaParaTransformar) {
	//				stringBuilder.append(extra.getNombre());
	//				i++;
	//				if(i < listaParaTransformar.size()) {
	//					stringBuilder.append(", ");
	//				}else {
	//					stringBuilder.append(".");
	//				}
	//			}
	//			stringBuilder.append("El evento no incluye ningun otro servicio.");
	//		}
	//		return stringBuilder;
	//	}

	public void enviarMailComprabantePago(Pago pago, List<Pago> listaPagos) {

		Evento evento = pago.getEvento();

		String diaPago = DateUtil.getFecha(pago.getFecha());
		String horaPago = DateUtil.getHorario(pago.getFecha());

		String dia = DateUtil.getFecha(evento.getStartd());
		String horaInicio = DateUtil.getHorario(evento.getStartd());
		String horaFin = DateUtil.getHorario(evento.getEndd());

		int totalPago = 0;
		for(Pago pagos : listaPagos) {
			totalPago += pagos.getPago();
		}

		Email emailBody = new Email();
		emailBody.setEmail(evento.getCliente().getEmail());
		emailBody.setSubject("Tu pago del evento  " + evento.getNombre() + ", codigo: " + evento.getCodigo());
		emailBody.setContent(
				"Tu pago para el evento: " + evento.getNombre() + " ha sido realizado exitosamente." + "<br>" +
						"Fecha de pago: " + diaPago + " hora: " + horaPago + "<br>" +
						"Monto abonado: $" + pago.getPago() + "<br>" +
						"Monto total abonado hasta la fecha: $" + totalPago  + "<br>" +
						"Monto faltante: $" + Math.abs(evento.getPresupuesto() - totalPago)  + "<br>" +
						"El precio total del evento: $" + evento.getPresupuesto() + "<br>" +
						"Acercate cuando quieras al salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
						"Te recordamos que tu evento se realizara el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>");

		this.sendEmail(emailBody);
	}

	public void enviarMailEventoEliminado(Evento evento) {

		String dia = DateUtil.getFecha(evento.getStartd());
		String horaInicio = DateUtil.getHorario(evento.getStartd());
		String horaFin = DateUtil.getHorario(evento.getEndd());

		Email emailBody = new Email();
		emailBody.setEmail(evento.getCliente().getEmail());
		emailBody.setSubject("El evento  " + evento.getNombre() + ", codigo: " + evento.getCodigo() + " fue cancelado con exito");
		emailBody.setContent(
				"El evento: " + evento.getNombre() + " ha sido cancelado exitosamente." + "<br>" +
						"tu evento se iba a realizar el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
						"Ante cualquier consulta" + "<br>" +
						"Acercate al salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>");

		this.sendEmail(emailBody);
	}

}