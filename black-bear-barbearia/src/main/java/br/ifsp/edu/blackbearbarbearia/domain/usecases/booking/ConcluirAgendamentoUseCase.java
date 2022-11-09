package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.math.BigDecimal;

public class ConcluirAgendamentoUseCase {
    private final BookingDAO dao;
    public ConcluirAgendamentoUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public BigDecimal update(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = booking.getId();
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Booking not found.");

        booking.setPaid(true);
        dao.update(booking);

        Service service = booking.getService();

        BigDecimal price = service.getPrice();
        BigDecimal comissionPercentage = service.getComissionPercentage();

        BigDecimal commission = price.multiply(comissionPercentage);

        return commission;
    }
}
