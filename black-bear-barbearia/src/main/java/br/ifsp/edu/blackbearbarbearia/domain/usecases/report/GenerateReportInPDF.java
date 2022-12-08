package br.ifsp.edu.blackbearbarbearia.domain.usecases.report;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class GenerateReportInPDF {
    private Random id = new Random();

    public void generate(List<Booking> bookings) throws FileNotFoundException {
        Document REPORT = new Document();

        PdfWriter.getInstance(REPORT, new FileOutputStream("report" + id.nextInt(10000) + ".pdf"));

        REPORT.open();

        Font title = new Font(Font.TIMES_ROMAN, 18);
        Font sessionTitle = new Font(Font.COURIER, 14);
        Font contrastParagraph = new Font(Font.TIMES_ROMAN, 14);
        Font paragraph = new Font(Font.HELVETICA, 12);

        Paragraph documentTitle = new Paragraph();
        documentTitle.setAlignment(Element.ALIGN_CENTER);
        documentTitle.add(new Chunk("REPORT", title));
        REPORT.add(documentTitle);
        REPORT.add(new Paragraph(" "));

        Booking booking;
        double total = 0;
        for(int i = 0; i < bookings.size(); i++) {
            booking = bookings.get(0);
            Paragraph serviceHeader = new Paragraph();
            serviceHeader.add(new Chunk("SERVICE #" + i, sessionTitle));
            REPORT.add(serviceHeader);

            Paragraph serviceElement = new Paragraph();
            serviceElement.add(new Chunk("       Service name: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getService() + "\n", paragraph));

            serviceElement.add(new Chunk("       Employee name: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getEmployee() + "\n", paragraph));

            serviceElement.add(new Chunk("       Date: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getDate() + "\n", paragraph));

            serviceElement.add(new Chunk("       Hour: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getHour() + "\n", paragraph));

            serviceElement.add(new Chunk("       Client name: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getClient() + "\n", paragraph));

            serviceElement.add(new Chunk("       Price: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getInfoService().getPrice().toString(), paragraph));
            REPORT.add(serviceElement);

            total = total + booking.getInfoService().calculateComission().doubleValue();
        }

        Paragraph serviceFooter = new Paragraph();
        serviceFooter.setAlignment(Element.ALIGN_RIGHT);
        serviceFooter.add(new Chunk("Quantity: " + bookings.size() + "\n", new Font(Font.COURIER, 18)));
        serviceFooter.add(new Chunk(String.format("Commission: R$%.2f", total), new Font(Font.COURIER, 18)));
        REPORT.add(serviceFooter);

        REPORT.close();
    }
}
