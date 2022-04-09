package com.estonianport.geservapp.email;

import java.util.List;

import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;

public interface EmailPort {

	void sendEmail(EmailBody emailBody);

	void enviarMailComprabanteReserva(Evento evento, List<Extra> listaExtra);

}
