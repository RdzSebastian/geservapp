
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
		Set<ExtraSubTipoEvento> listaExtra = evento.getListaExtraSubTipoEvento();
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
		StringBuilder extraVariableMail = new StringBuilder();

		if(listaEventoExtraVariable != null && !listaEventoExtraVariable.isEmpty()) {

			Set<ExtraVariableSubTipoEvento> listaExtraVariable = new HashSet<ExtraVariableSubTipoEvento>();

			for(EventoExtraVariableSubTipoEvento eventoExtraVariableSubTipoEvento : listaEventoExtraVariable) {
				listaExtraVariable.add(eventoExtraVariableSubTipoEvento.getExtraVariableSubTipoEvento());
			}

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
			}		
		}
		else {
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
			StringBuilder extraVariableCateringMail = new StringBuilder();

			if(listaCateringExtraCatering != null && !listaCateringExtraCatering.isEmpty()) {

				Set<ExtraVariableCatering> listaExtraCatering = new HashSet<ExtraVariableCatering>();

				for(CateringExtraVariableCatering cateringExtraVariableCatering : listaCateringExtraCatering) {
					listaExtraCatering.add(cateringExtraVariableCatering.getExtraVariableCatering());
				}

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
				}
			}else {
				extraVariableCateringMail.append("El evento no incluye extra catering.");
			}

			catering.append("El catering contratado es el siguiente: ");
			catering.append("<br>");

			if(tipoCateringMail.length() != 0) {
				catering.append(tipoCateringMail);
				catering.append("<br>");
			}

			if(extraVariableCateringMail.length() != 0) {
				catering.append(extraVariableCateringMail);
				catering.append("<br>");
			}
		}else {
			catering.append("El evento no incluye catering");
		}

		// ------------------- Presupuesto -----------------------
		int presupuestoTotal = 0;

		if(evento.getPresupuesto() != 0) {
			presupuestoTotal += evento.getPresupuesto();
		}

		if(evento.getCatering() != null && evento.getCatering().getPresupuesto() != 0) {
			presupuestoTotal += evento.getCatering().getPresupuesto();
		}

		// ------------------- Dia y hora ---------------------------
		String dia = DateUtil.getFecha(evento.getStartd());
		String horaInicio = DateUtil.getHorario(evento.getStartd());
		String horaFin = DateUtil.getHorario(evento.getEndd());

		// ----------------- Armado de mail -------------------------
		Email emailBody = new Email();
		emailBody.setEmail(evento.getCliente().getEmail());
		emailBody.setSubject("Tu evento: " +  evento.getNombre() + " para el " + dia + ", codigo: " + evento.getCodigo());

		// ----------------- Style del mail -------------------------
		StringBuilder contentEmail = new StringBuilder();

		contentEmail.append(this.createHeadWithStyle());
		contentEmail.append(this.createBody());
		contentEmail.append(this.createHeader());
		contentEmail.append(this.createTitle(evento.getNombre(), action));
		contentEmail.append(this.createComprobante(evento.getCodigo(), evento.getSubTipoEvento().getNombre(), evento.getCapacidad().getCapacidadNinos(), evento.getCapacidad().getCapacidadAdultos(), presupuestoTotal));
		contentEmail.append(this.createSalon(evento.getSalon().getNombre(), evento.getSalon().getCalle(), evento.getSalon().getNumero(), evento.getSalon().getMunicipio()));
		contentEmail.append(this.createExtras(extraMail, extraVariableMail));
		contentEmail.append(this.createCatering(catering));
		contentEmail.append(this.createServicios(servicioMail));
		contentEmail.append(this.createFooter());

		//emailBody.setContent(contentEmail.toString());

		emailBody.setContent(
				"Tu evento: " + evento.getNombre() + ", ha " + action + " exitosamente." + "<br>" +
						"Codigo: " + evento.getCodigo() + "<br>" +
						"Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
						"En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
						"Contrataste un evento " + evento.getSubTipoEvento().getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
						"Para " + evento.getCapacidad().getCapacidadAdultos() +  " adultos y " + evento.getCapacidad().getCapacidadNinos() + " niños." + "<br>" +
						"<br>" +
						extraMail + "<br>" +
						extraVariableMail + "<br>" +
						"<br>" +
						catering + "<br>" +
						"<br>" +
						"El precio final del evento es: $" + presupuestoTotal + "<br>" +
						"<br>" +
						servicioMail + "<br>");

		this.sendEmail(emailBody);
	}



	private StringBuilder createHeadWithStyle() {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<!DOCTYPE html>"
				+ "<html lang='es'> "
				+ "<head>"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />"
				+ "<meta name='viewport' content='width=device-width; initial-scale=1.0; maximum-scale=1.0;' />"
				+ "<!--[if !mso]--><!-- -->"
				+ "<link href='https://fonts.googleapis.com/css?family=Work+Sans:300,400,500,600,700' rel='stylesheet'>"
				+ "<link href='https://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel='stylesheet'>"
				+ "<!-- <![endif]-->"
				+ "<title>Geservapp mail</title>");

		contentMain.append("<style type='text/css'>"
				+ "body {"
				+ "	width: 100%;"
				+ "	background-color: #ffffff;"
				+ "	margin: 0;"
				+ "	padding: 0;"
				+ "	-webkit-font-smoothing: antialiased;"
				+ "	mso-margin-top-alt: 0px;"
				+ "	mso-margin-bottom-alt: 0px;"
				+ "	mso-padding-alt: 0px 0px 0px 0px;"
				+ "}"
				+ "p, h1,h2, h3, h4 {"
				+ "	margin-top: 0;"
				+ "	margin-bottom: 0;"
				+ "	padding-top: 0;"
				+ "	padding-bottom: 0;"
				+ "}"
				+ "span.preheader {"
				+ "	display: none;"
				+ "	font-size: 1px;"
				+ "}"
				+ "html {"
				+ "	width: 100%;"
				+ "}"
				+ "table {"
				+ "	font-size: 14px;"
				+ "	border: 0;"
				+ "}"
				+ "media only screen and (max-width: 640px) {" /* ----------- responsivity ----------- */
				+ " .main-header {" /*------ top header ------ */
				+ "	 font-size: 20px !important;"
				+ " }"
				+ " .main-section-header {"
				+ "	 font-size: 28px !important;"
				+ " }"
				+ " .show {"
				+ "	 display: block !important;"
				+ " }"
				+ " .hide {"
				+ "	 display: none !important;"
				+ " }"
				+ " .align-center {"
				+ "	 text-align: center !important;"
				+ " }"
				+ " .no-bg {"
				+ "	 background: none !important;"
				+ " }"
				+ " .main-image img {" /*----- main image -------*/
				+ "	 width: 440px !important;"
				+ "	 height: auto !important;"
				+ " }"
				+ " .divider img {" /* ====== divider ====== */
				+ "	 width: 440px !important;"
				+ " }"
				+ " .container590 {" /*-------- container --------*/
				+ "	 width: 440px !important;"
				+ " }"
				+ " .container580 {"
				+ "	 width: 400px !important;"
				+ " }"
				+ " .main-button {"
				+ "	 width: 220px !important;"
				+ " }"
				+ " .section-img img {" /*-------- secions ----------*/
				+ "	 width: 320px !important;"
				+ "	 height: auto !important;"
				+ " }"
				+ " .team-img img {"
				+ "	 width: 100% !important;"
				+ "	 height: auto !important;"
				+ " }"
				+ "}"
				+ "@media only screen and (max-width: 479px) {"
				+ " .main-header {"/*------ top header ------ */
				+ "  font-size: 18px !important;"
				+ " }"
				+ " .main-section-header {"
				+ "  font-size: 26px !important;"
				+ " }"
				+ " .divider img {" /* ====== divider ====== */
				+ "  width: 280px !important;"
				+ " }"
				+ " .container590 {" /*-------- container --------*/
				+ "  width: 280px !important;"
				+ " }"
				+ " .container590 {"
				+ "  width: 280px !important;"
				+ " }"
				+ " .container580 {"
				+ "  width: 260px !important;"
				+ " }"
				+ " .section-img img {" /*-------- secions ----------*/
				+ "  width: 280px !important;"
				+ "  height: auto !important;"
				+ " }"
				+ "}"
				+ "</style>"
				+ "<!-- [if gte mso 9]><style type=”text/css”>"
				+ "body {"
				+ " font-family: arial, sans-serif!important;"
				+ "}"
				+ "</style>"
				+ "<![endif]-->"
				+ "</head>");

		return contentMain;
	}

	private StringBuilder createHeader() {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center' height='70' style='height:70px;'>"
				+ "<a href='' style='display: block; border-style: none !important; border: 0 !important;'><img width='100' border='0' style='display: block; width: 100px;' src='https://mdbootstrap.com/img/logo/mdb-email.png' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createBody() {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<body class='respond' leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>");

		return contentMain;
	}

	private StringBuilder createTitle(String eventoNombre, String action) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createComprobante(String codigo, String subTipoEventoNombre, int capacidadAdultos, int capacidadNinos, int presupuestoTotal) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createExtras(StringBuilder extraMail, StringBuilder extraVariableMail) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createCatering(StringBuilder catering) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createSalon(String salonNombre, String calle, String numero, String municipio) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createServicios(StringBuilder servicioMail) {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

	private StringBuilder createFooter() {
		StringBuilder contentMain = new StringBuilder();

		return contentMain;
	}

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