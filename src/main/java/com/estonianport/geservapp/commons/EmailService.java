
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
			servicioMail.append("El evento no incluye ningun otro servicio.");
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

		// ----------------- Imagenes del mail -------------------------
		
		String imagenLogo = "https://i.ibb.co/djW4JkC/logo.png";
		String imagenComprobante = "https://i.ibb.co/9TLSqHy/comprobante.jpg";
		String imagenSalon = "https://i.ibb.co/wLKMCP4/salon.jpg";
		String imagenExtra = "https://i.ibb.co/92R9Yj3/extras.jpg";
		String imagenCatering = "https://i.ibb.co/pJdbbmB/catering.jpg";
		String imagenServicio = "https://i.ibb.co/dmtxW9z/servicios.jpg";
		
		String imagenIg = "https://i.ibb.co/8Xb3WwH/ig.png";
		String imagenWpp = "https://i.ibb.co/pnZg9Nr/wpp.png";
		String imagenFb = "https://i.ibb.co/dB90K2y/fb.png";
		String imagenMail = "https://i.ibb.co/bWgGQv6/mail.png";
		
		// ----------------- Style del mail -------------------------
		StringBuilder contentEmail = new StringBuilder();

		contentEmail.append(this.createHeadWithStyle());
		contentEmail.append(this.createBody());
		contentEmail.append(this.createHeader(imagenLogo));
		contentEmail.append(this.createTitle(evento.getNombre(), action));
		contentEmail.append(this.createComprobante(evento.getCodigo(), evento.getSubTipoEvento().getNombre(), evento.getCapacidad().getCapacidadAdultos(), evento.getCapacidad().getCapacidadNinos(), presupuestoTotal, dia, horaInicio, horaFin, imagenComprobante));
		contentEmail.append(this.createSalon(evento.getSalon().getNombre(), evento.getSalon().getCalle(), evento.getSalon().getNumero(), evento.getSalon().getMunicipio(), imagenSalon));
		contentEmail.append(this.createExtras(extraMail, extraVariableMail, imagenExtra));
		contentEmail.append(this.createCatering(catering, imagenCatering));
		contentEmail.append(this.createServicios(servicioMail, imagenServicio));
		contentEmail.append(this.createContact(imagenLogo, imagenIg, imagenWpp, imagenFb, imagenMail));
		contentEmail.append(this.createFooter());
		contentEmail.append("</body>	</html>");


		
		emailBody.setContent(contentEmail.toString());

		//		emailBody.setContent(
		//				"Tu evento: " + evento.getNombre() + ", ha " + action + " exitosamente." + "<br>" +
		//						"Codigo: " + evento.getCodigo() + "<br>" +
		//						"Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "." + "<br>" +
		//						"En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "." + "<br>" +
		//						"Contrataste un evento " + evento.getSubTipoEvento().getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "." + "<br>" +
		//						"Para " + evento.getCapacidad().getCapacidadAdultos() +  " adultos y " + evento.getCapacidad().getCapacidadNinos() + " niños." + "<br>" +
		//						"<br>" +
		//						extraMail + "<br>" +
		//						extraVariableMail + "<br>" +
		//						"<br>" +
		//						catering + "<br>" +
		//						"<br>" +
		//						"El precio final del evento es: $" + presupuestoTotal + "<br>" +
		//						"<br>" +
		//						servicioMail + "<br>");

		this.sendEmail(emailBody);
	}



	private StringBuilder createHeadWithStyle() {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append(
			"<!DOCTYPE html>"
				+ "<html lang='es'> "
				+ "<head>"
				+ "	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />"
				+ "	<meta name='viewport' content='width=device-width; initial-scale=1.0; maximum-scale=1.0;' />"
				+ " <!--[if !mso]--><!-- -->"
				+ " <link href='https://fonts.googleapis.com/css?family=Work+Sans:300,400,500,600,700' rel='stylesheet'>"
				+ " <link href='https://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel='stylesheet'>"
				+ " <!-- <![endif]-->"
				+ " <title>Geservapp mail</title>");

		contentMain.append(
			"<style type='text/css'>"
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
				+ "p, h1, h2, h3, h4 {"
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
				+ "@media only screen and (max-width: 640px) {" /* ----------- responsivity ----------- */
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
				+ " body {"
				+ "  font-family: arial, sans-serif!important;"
				+ " }"
				+ "<![endif]-->"
				+ "</head>");

		return contentMain;
	}

	private StringBuilder createHeader(String imagenLogo) {
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
				+ "<a href='' style='display: block; border-style: none !important; border: 0 !important;'><img width='100' border='0' style='display: block; width: 100px;' src='" + imagenLogo + "' alt='' /></a>"
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

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff' class='bg_color'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center' style='color: #343434; font-size: 24px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;' class='main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "TU <span style='color: #5caad2;'>COMPROBANTE</span>"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='10' style='font-size: 10px; line-height: 10px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' width='40' align='center' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' width='400' align='center' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center' style='color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;'>"
				+ "<div style='line-height: 24px'>"
				+ "Tu evento: " + eventoNombre + ", ha " + action + " exitosamente."
				+ "</div>"
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
				+ "<tr class='hide'>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createComprobante(String codigo, String subTipoEventoNombre, int capacidadAdultos, int capacidadNinos, int presupuestoTotal, String dia, String horaInicio, String horaFin, String imagenComprobante) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<a href='' style=' border-style: none !important; border: 0 !important;'><img src='" + imagenComprobante + "' style='display: block; width: 280px;' width='280' border='0' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='5' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;'"
				+ "class='container590'>"
				+ "<tr>"
				+ "<td width='5' height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='260' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;'"
				+ "class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #3d3d3d; font-size: 22px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;'class='align-center main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "RESERVA"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left'>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' width='40' border='0' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;' class='align-center'>"
				+ "<div style='line-height: 24px'>"
				+ "Codigo de reserva: " + codigo
				+ " </div>"
				+ "<div style='line-height: 24px'>"
				+ "Fecha de evento: " + dia
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "de " + horaInicio + " a " + horaFin
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "Contrataste un " + subTipoEventoNombre
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "Para " + capacidadAdultos + " adultos y " + capacidadNinos + " niños"
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "Precio final: " + presupuestoTotal
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createSalon(String salonNombre, String calle, String numero, String municipio, String imagenSalon) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<a href='' style=' border-style: none !important; border: 0 !important;'><img src='" + imagenSalon + "' style='display: block; width: 280px;' width='280' border='0' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='5' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td width='5' height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='260' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #3d3d3d; font-size: 22px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;' class='align-center main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "SALON"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left'>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' width='40' border='0' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;' class='align-center'>"
				+ "<div style='line-height: 24px'>"
				+ "Nombre de salon: " + salonNombre
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "Direccion: " + calle + " " + numero
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ "Localidad: " + municipio 
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createExtras(StringBuilder extraMail, StringBuilder extraVariableMail, String imagenExtra) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<a href='' style=' border-style: none !important; border: 0 !important;'><img src='" + imagenExtra + "' style='display: block; width: 280px;' width='280' border='0' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='5' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td width='5' height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='260' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #3d3d3d; font-size: 22px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;' class='align-center main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "EXTRAS"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left'>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' width='40' border='0' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;' class='align-center'>"
				+ "<div style='line-height: 24px'>"
				+ extraMail
				+ "</div>"
				+ "<div style='line-height: 24px'>"
				+ extraVariableMail
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createCatering(StringBuilder cateringMail, String imagenCatering) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<a href='' style=' border-style: none !important; border: 0 !important;'><img src='" + imagenCatering + "' style='display: block; width: 280px;' width='280' border='0' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='5' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td width='5' height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='260' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #3d3d3d; font-size: 22px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;' class='align-center main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "CATERING"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left'>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' width='40' border='0' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;' class='align-center'>"
				+ "<div style='line-height: 24px'>"
				+ cateringMail
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createServicios(StringBuilder servicioMail, String imagenServicio) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<a href='' style=' border-style: none !important; border: 0 !important;'><img src='" + imagenServicio + "' width='280' border='0' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='5' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td width='5' height='20' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='260' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #3d3d3d; font-size: 22px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;' class='align-center main-header'>"
				+ "<div style='line-height: 35px'>"
				+ "SERVICIOS"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left'>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' width='40' border='0' cellpadding='0' cellspacing='0' bgcolor='eeeeee'>"
				+ "<tr>"
				+ "<td height='2' style='font-size: 2px; line-height: 2px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 12px; line-height: 12px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;' class='align-center'>"
				+ "<div style='line-height: 24px'>"
				+ servicioMail
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}



	private StringBuilder createContact(String imagenLogo, String imagenIg, String imagenWpp, String imagenFb, String imagenMail) {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='ffffff' class='bg_color'>"
				+ "<tr class='hide'>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='40' style='font-size: 40px; line-height: 40px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='60' style='border-top: 1px solid #e0e0e0;font-size: 60px; line-height: 60px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590 bg_color'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' width='300' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left'>" /* logo */
				+ "<a href='' style='display: block; border-style: none !important; border: 0 !important;'><img width='80' border='0' style='display: block; width: 80px;' src='" + imagenLogo + "' alt='' /></a>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>"
				+ "&nbsp;"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ " <td align='left' style='color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 23px;' class='text_color'>"
				+ "<div style='color: #333333; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; font-weight: 300; mso-line-height-rule: exactly; line-height: 23px;'>"
				+ "Hablanos a nuestras redes sociales:"
				+ "</div>"
				+ " </td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='2' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td width='2' height='10' style='font-size: 10px; line-height: 10px;'></td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' width='200' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td class='hide' height='45' style='font-size: 45px; line-height: 45px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='15' style='font-size: 15px; line-height: 15px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='right' cellpadding='0' cellspacing='0'>"
				+ "<tr>"
				+ "<td>"
				+ "<a href='https://www.instagram.com/mix_eventos_/?hl=es' style='display: block; border-style: none !important; border: 0 !important;'><img width='24' border='0' style='display: block;' src='" + imagenIg + "' alt=''></a>"
				+ "</td>"
				+ "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
				+ "<td>"
				+ "<a href='https://www.facebook.com/mixeventoscaseros/' style='display: block; border-style: none !important; border: 0 !important;'><img width='24' border='0' style='display: block;' src='" + imagenFb + "' alt=''></a>"
				+ "</td>"
				+ "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
				+ "<td>"
				+ "<a href='http://bit.ly/MixEventosCaseros' style='display: block; border-style: none !important; border: 0 !important;'><img width='24' border='0' style='display: block;' src='" + imagenWpp + "'alt=''></a>"
				+ "</td>"
				+ "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
				+ "<td>"
				+ "<td>"
				+ "<a href='mailto:miixeventos1@gmail.com' style='display: block; border-style: none !important; border: 0 !important;'><img width='24' border='0' style='display: block;' src='" + imagenMail + "' alt=''></a>"
				+ "</td>"
				+ "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='60' style='font-size: 60px; line-height: 60px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

		return contentMain;
	}

	private StringBuilder createFooter() {
		StringBuilder contentMain = new StringBuilder();

		contentMain.append("<table border='0' width='100%' cellpadding='0' cellspacing='0' bgcolor='f4f4f4'>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table border='0' align='center' width='590' cellpadding='0' cellspacing='0' class='container590'>"
				+ "<tr>"
				+ "<td>"
				+ "<table border='0' align='left' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='left' style='color: #aaaaaa; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;'>"
				+ "<div style='line-height: 24px;'>"
				+ "<span style='color: #333333;'>"
				+ "Geservapp diseñado por"
				+ "</span>"
				+ "</div>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' align='left' width='5' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td height='20' width='5' style='font-size: 20px; line-height: 20px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>"
				+ "<table border='0' align='right' cellpadding='0' cellspacing='0' style='border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='container590'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<table align='center' border='0' cellpadding='0' cellspacing='0'>"
				+ "<tr>"
				+ "<td align='center'>"
				+ "<span style='font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;color: #5caad2; text-decoration: none;font-weight:bold;'>"
				+ "Estonian Port"
				+ "</span>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "</table>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td height='25' style='font-size: 25px; line-height: 25px;'>&nbsp;</td>"
				+ "</tr>"
				+ "</table>");

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