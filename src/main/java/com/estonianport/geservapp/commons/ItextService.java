package com.estonianport.geservapp.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.estonianport.geservapp.container.ReservaContainer;
import com.estonianport.geservapp.model.Evento;
import com.estonianport.geservapp.model.Extra;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ItextService {
	Random ramdom = new Random();
	public String DIRECTORY_PDF = "pdf/";
	public String EXTENSION_PDF = ".pdf";

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public void createPdf(ReservaContainer reservaContainer) throws Exception {
    	//Crear Archivo
    	Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(DIRECTORY_PDF + reservaContainer.getEvento().getCodigo() + EXTENSION_PDF));
        document.open();
        this.addMetaData(document);
        this.addTitlePage(document, reservaContainer.getEvento());
        this.addContent(document, reservaContainer.getEvento(), reservaContainer.getExtra());
        document.close();
    }

    public void addMetaData(Document document) {
        document.addTitle("Comprobante de reserva");
        document.addSubject("Salon");
        document.addKeywords("Comprobante, Reserva, Eventos");
        document.addAuthor("Salon");
        document.addCreator("Salon");
    }

    public void addTitlePage(Document document, Evento evento) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        
		String dia = LocalDateTime.now().getDayOfMonth() + "-" + LocalDateTime.now().getMonthValue() + "-" +  LocalDateTime.now().getYear();
		String hora = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
		
        // Titulo
        paragraph.add(new Paragraph("Comprobante de reserva " + evento.getSalon().getNombre(), catFont));
        addEmptyLine(paragraph, 1);
        
        // Quien realizo el presupuesto y fecha
        paragraph.add(new Paragraph("Presupuesto realizado por: " + evento.getUsuario().getApellido() + ", " + evento.getUsuario().getNombre() + " el dia " + dia + " a las " + hora, smallBold));
        addEmptyLine(paragraph, 2);
        
        document.add(paragraph);
    }

    public void addContent(Document document, Evento evento, List<Extra> listaExtra) throws DocumentException {

		String dia = evento.getStart_date().getDayOfMonth() + "-" + evento.getStart_date().getMonth().getValue() + "-" + evento.getStart_date().getYear();
		String horaInicio = String.valueOf(evento.getStart_date().getHour()) + ":" +String.valueOf(evento.getStart_date().getMinute());
		String horaFin = String.valueOf(evento.getEnd_date().getHour()) + ":" +String.valueOf(evento.getEnd_date().getMinute());

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph("Tu evento: " + evento.getNombre()));
        paragraph.add(new Paragraph("Codigo: " + evento.getCodigo(), smallBold));
        paragraph.add(new Paragraph("Te esperamos el dia " + dia + " de " + horaInicio + " a " + horaFin + "."));
        paragraph.add(new Paragraph("En el salon: " + evento.getSalon().getNombre() + " en calle " + evento.getSalon().getCalle() + " " + evento.getSalon().getNumero() + ", " + evento.getSalon().getMunicipio() + "."));
        paragraph.add(new Paragraph("Contrataste un evento " + evento.getTipoEvento().getNombre() +  ", " + evento.getSubTipoEvento().getNombre() + "."));
       
        addEmptyLine(paragraph, 1);
        createTable(paragraph, listaExtra);
        addEmptyLine(paragraph, 1);

        paragraph.add(new Paragraph("El precio final del evento es: " + evento.getPresupuesto()));

        
        document.add(paragraph);

    }

    public void createTable(Paragraph paragraph, List<Extra> listaExtra) throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Phrase("Extra"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Precio"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

		for(Extra extra : listaExtra) {
			 table.addCell(extra.getNombre());
			 table.addCell(Integer.toString(extra.getPrecio()));
		}
        paragraph.add(table);
    }

    public void deletePdf(String codigo) throws Exception {
    	File file = new File(DIRECTORY_PDF + codigo + EXTENSION_PDF);
    	file.delete();
    }

    public void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
