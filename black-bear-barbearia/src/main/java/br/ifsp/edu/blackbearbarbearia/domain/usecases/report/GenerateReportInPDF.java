package br.ifsp.edu.blackbearbarbearia.domain.usecases.report;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class GenerateReportInPDF {
    private Document REPORT;

    public void generate(List<Booking> bookings) {
        if (REPORT == null)
            REPORT = new Document();

        try {
            PdfWriter.getInstance(REPORT, new FileOutputStream(bookings.get(0).getEmployee() + ".pdf"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        REPORT.open();

        Font title = new Font(Font.TIMES_ROMAN, 18);
        Font sessionTitle = new Font(Font.COURIER, 14);
        Font contrastParagraph = new Font(Font.TIMES_ROMAN, 14);
        Font paragraph = new Font(Font.HELVETICA, 12);

        Booking booking;
        BigDecimal total = new BigDecimal(0);
        for(int i = 0; i < bookings.size(); i++) {
            booking = bookings.get(0);
            Paragraph serviceHeader = new Paragraph();
            serviceHeader.add(new Chunk("SERVICE #" + i, sessionTitle));
            REPORT.add(serviceHeader);

            Paragraph serviceElement = new Paragraph();
            serviceElement.add(new Chunk("       Nome do serviço: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getService() + "\n", paragraph));

            serviceElement.add(new Chunk("       Nome do funcionário: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getEmployee() + "\n", paragraph));

            serviceElement.add(new Chunk("       Data: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getDate(), paragraph));

            serviceElement.add(new Chunk("       Horário: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getHour(), paragraph));

            serviceElement.add(new Chunk("       Nome do cliente: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getClient(), paragraph));

            serviceElement.add(new Chunk("       Valor: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getInfoService().getPrice().toString(), paragraph));
            REPORT.add(serviceElement);

            total.add(booking.getInfoService().getPrice());
        }

        Paragraph serviceFooter = new Paragraph();
        serviceFooter.setAlignment(Element.ALIGN_RIGHT);
        serviceFooter.add(new Chunk("Quantidade: " + bookings.size(), new Font(Font.COURIER, 18)));
        serviceFooter.add(new Chunk(String.format("R$%.2f", total), new Font(Font.COURIER, 18)));
        REPORT.add(serviceFooter);

        REPORT.close();
    }
}
