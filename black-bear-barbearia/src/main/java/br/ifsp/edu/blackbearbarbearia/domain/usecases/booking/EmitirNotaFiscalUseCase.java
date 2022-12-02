package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.client.Client;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;


public class EmitirNotaFiscalUseCase {
    private Map<String, String> notaFiscal;

    public Map<String, String> create(Booking booking) {
        notaFiscal = new LinkedHashMap<>();

        notaFiscal.put("Date", booking.getDate());

        String client = booking.getClient();
        notaFiscal.put("Client", client);

        String user = booking.getEmployee();
        notaFiscal.put("Employee", user);

        Service service = booking.getInfoService();
        notaFiscal.put("Service", service.getNome());
        notaFiscal.put("Price", service.getPrice().toString());

        return notaFiscal;
    }
}
