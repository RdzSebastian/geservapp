package com.estonianport.geservapp;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.estonianport.geservapp.commons.CodeGenerator;
import com.estonianport.geservapp.commons.ItextService;
import com.estonianport.geservapp.model.Evento;
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

    		Evento evento = new Evento();
    		evento.setCodigo(CodeGenerator.GetBase26Only4Letters());
    		evento.setNombre("event test");

    		evento.setStart_date(LocalDateTime.of(2022,02,01,20,30));
    		evento.setEnd_date(LocalDateTime.of(2022,02,01,22,30));
    		evento.setTipoEvento(tipoEventoService.get((long) 1));
    		evento.setSubTipoEvento(subTipoEventoService.get((long) 1));
    		evento.setSalon(salonService.get((long) 1));

    		// Agrega Extras
    		Set<Extra> listaExtra = new HashSet<Extra>();

    		Extra extraAnimacion = new Extra();
    		extraAnimacion.setId((long) 1);
    		extraAnimacion.setNombre("Animacion");
    		extraAnimacion.setPrecio(100);

    		listaExtra.add(extraAnimacion);
    		
    		Extra extraPersonal = new Extra();
    		extraPersonal.setId((long) 1);
    		extraPersonal.setNombre("Personal");
    		extraPersonal.setPrecio(100);

    		listaExtra.add(extraPersonal);
    		
    		evento.setListaExtra(listaExtra);
    		
    		evento.setPresupuesto(1000);

    		evento.setUsuario(usuarioService.get((long) 1));

            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(itextService.DIRECTORY_PDF + evento.getCodigo() + itextService.EXTENSION_PDF));
            document.open();
            
            itextService.addMetaData(document);
            itextService.addTitlePage(document, evento);
            itextService.addContent(document, evento, evento.getListaExtra());
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
