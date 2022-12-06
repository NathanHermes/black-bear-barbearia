package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CancelBookingUseCase {
    private final BookingDAO dao;
    public CancelBookingUseCase(BookingDAO dao) {
        this.dao = dao;
    }

    public boolean update(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = booking.getId();
        if (dao.findOne(id).isEmpty())
            throw new EntityNotFoundException("Booking not found.");

        Booking daoBooking = dao.findOne(id).get();
        if (daoBooking.getStatus().equals(Status.DONE))
            throw new IllegalArgumentException("Booking has already been completed");

        daoBooking.setStatus(Status.CANCELLED);
        return dao.update(daoBooking);
    }
}