package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GenerateNotaFiscalInPDFUseCase {
    private Document NOTA_FISCAL;

    public void generate(Booking booking) {
        if (NOTA_FISCAL == null)
            NOTA_FISCAL = new Document();

        try {
            PdfWriter.getInstance(NOTA_FISCAL, new FileOutputStream(booking.getId() + booking.getClient() + ".pdf"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        NOTA_FISCAL.open();

        Font title = new Font(Font.TIMES_ROMAN, 18);
        Font sessionTitle = new Font(Font.COURIER, 14);
        Font contrastParagraph = new Font(Font.TIMES_ROMAN, 14);
        Font paragraph = new Font(Font.HELVETICA, 12);

        Paragraph documentTitle = new Paragraph();
        documentTitle.setAlignment(Element.ALIGN_CENTER);
        documentTitle.add(new Chunk("NOTA FISCAL BLACK BEAR", title));
        NOTA_FISCAL.add(documentTitle);
        NOTA_FISCAL.add(new Paragraph(" "));

        Paragraph clientHeader = new Paragraph();
        clientHeader.add(new Chunk("CLIENTE", sessionTitle));
        NOTA_FISCAL.add(clientHeader);

        Paragraph clientElement = new Paragraph();
        clientElement.add(new Chunk("       Nome: ", contrastParagraph));
        clientElement.add(new Chunk(booking.getClient() + "\n", paragraph));

        clientElement.add(new Chunk("       Email: ", contrastParagraph));
        clientElement.add(new Chunk(booking.getInfoClient().getEmail() + "\n", paragraph));

        clientElement.add(new Chunk("       Telefone: ", contrastParagraph));
        clientElement.add(new Chunk(booking.getInfoClient().getPhone(), paragraph));
        NOTA_FISCAL.add(clientElement);
        NOTA_FISCAL.add(new Paragraph(" "));

        Paragraph employeeHeader = new Paragraph();
        employeeHeader.add(new Chunk("FUNCIONÁRIO", sessionTitle));
        NOTA_FISCAL.add(employeeHeader);

        Paragraph employeeElement = new Paragraph();
        employeeElement.add(new Chunk("     Nome: ", contrastParagraph));
        employeeElement.add(new Chunk(booking.getEmployee() + "\n", paragraph));

        employeeElement.add(new Chunk("     Email: ", contrastParagraph));
        employeeElement.add(new Chunk(booking.getInfoEmployee().getEmail() + "\n", paragraph));

        employeeElement.add(new Chunk("     Telefone: ", contrastParagraph));
        employeeElement.add(new Chunk(booking.getInfoEmployee().getPhone(), paragraph));
        NOTA_FISCAL.add(employeeElement);
        NOTA_FISCAL.add(new Paragraph(" "));

        Paragraph serviceHeader = new Paragraph();
        serviceHeader.add(new Chunk("# " + booking.getId(), sessionTitle));
        NOTA_FISCAL.add(serviceHeader);

        Paragraph serviceElement = new Paragraph();
        serviceElement.add(new Chunk("     Data: ", contrastParagraph));
        serviceElement.add(new Chunk(booking.getDate() + "\n", paragraph));

        /*
            serviceElement.add(new Chunk("     Horário: ", contrastParagraph));
            serviceElement.add(new Chunk(booking.getDate() + "\n", paragraph));
        */

        serviceElement.add(new Chunk("     Serviço: ", contrastParagraph));
        serviceElement.add(new Chunk(booking.getService() + "\n", paragraph));
        NOTA_FISCAL.add(serviceElement);
        NOTA_FISCAL.add(new Paragraph(" "));

        Paragraph serviceFooter = new Paragraph();
        serviceFooter.setAlignment(Element.ALIGN_RIGHT);
        serviceFooter.add(new Chunk(String.format("R$%.2f", booking.getInfoService().getPrice()), new Font(Font.COURIER, 18)));
        NOTA_FISCAL.add(serviceFooter);

        NOTA_FISCAL.close();
    }
}
