package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.math.BigDecimal;

public class FinishBookingUseCase {
    private final BookingDAO dao;
    public FinishBookingUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public BigDecimal update(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = booking.getId();
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Booking not found");

        Booking daoBooking = dao.findOne(id).get();
        if (daoBooking.getStatus().equals(Status.DONE))
            throw new IllegalArgumentException("Booking has already been completed");
        if (daoBooking.getStatus().equals(Status.CANCELLED))
            throw new IllegalArgumentException("Booking is cancelled");

        daoBooking.setPaid(Boolean.TRUE);
        daoBooking.setStatus(Status.DONE);
        dao.update(daoBooking);

        Service service = daoBooking.getInfoService();
        BigDecimal price = service.getPrice();
        BigDecimal commissionPercentage = service.getComissionPercentage();

        return price.multiply(commissionPercentage);
    }
}
