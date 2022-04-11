package com.estonianport.geservapp;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.itext.ItextService;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.EventoExtra;
import com.estonianport.geservapp.model.Extra;
import com.estonianport.geservapp.service.EventoService;
import com.estonianport.geservapp.service.ExtraService;
import com.estonianport.geservapp.service.SalonService;
import com.estonianport.geservapp.service.SubTipoEventoService;
import com.estonianport.geservapp.service.TipoEventoService;
import com.estonianport.geservapp.service.UsuarioService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@SpringBootTest
class ItextTest {

	@Autowired
	ItextService itextService;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	ExtraService extraService;
	
	@Autowired
	private TipoEventoService tipoEventoService;

	@Autowired
	private SubTipoEventoService subTipoEventoService;
	
	@Autowired
	private SalonService salonService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Test
	void testItext() {
        try {
            Document document = new Document();
            
            
            PdfWriter.getInstance(document, new FileOutputStream(itextService.FILE));
            document.open();

    		Evento evento = new Evento();
    		evento.setNombre("event test");

    		evento.setStart_date(LocalDateTime.of(2022,02,01,20,30));
    		evento.setEnd_date(LocalDateTime.of(2022,02,01,22,30));
    		evento.setTipoEvento(tipoEventoService.get((long) 1));
    		evento.setSubTipoEvento(subTipoEventoService.get((long) 1));
    		evento.setSalon(salonService.get((long) 1));

    		Set<EventoExtra> eventoExtra = new HashSet<EventoExtra>();

    		EventoExtra eventoExtra1 = new EventoExtra();
    		eventoExtra1.setExtra(extraService.findExtraByNombre("Animacion"));
    		eventoExtra.add(eventoExtra1);

    		EventoExtra eventoExtra2 = new EventoExtra();
    		eventoExtra2.setExtra(extraService.get((long) 2));
    		eventoExtra.add(eventoExtra2);

    		evento.setEventoExtra(eventoExtra);
    		
    		evento.setPresupuesto(1000);

            List<Extra> listaExtra = new ArrayList<Extra>();

            
    		for(EventoExtra eventoExtraLista : evento.getEventoExtra()) {
    			listaExtra.add(eventoExtraLista.getExtra());
    		}
    		
    		evento.setUsuario(usuarioService.get((long) 1));

            itextService.addMetaData(document);
            itextService.addTitlePage(document, evento);
            itextService.addContent(document, evento, listaExtra);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
