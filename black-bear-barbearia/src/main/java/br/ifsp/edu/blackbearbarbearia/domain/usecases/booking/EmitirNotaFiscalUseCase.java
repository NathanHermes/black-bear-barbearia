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

        notaFiscal.put("Date", booking.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());

        Client client = booking.getClient();
        notaFiscal.put("Client", client.getName());

        User user = booking.getUser();
        notaFiscal.put("Employee", user.getFullName());

        Service service = booking.getService();
        notaFiscal.put("Service", service.getNome());
        notaFiscal.put("Price", service.getPrice().toString());

        return notaFiscal;
    }
}
