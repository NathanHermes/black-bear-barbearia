package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.math.BigDecimal;

public class FinishBookingUseCase {
    private final BookingDAO bookingDAO;
    private final StatusDAO statusDAO;
    public FinishBookingUseCase(BookingDAO bookingDAO, StatusDAO statusDAO) {
        this.bookingDAO = bookingDAO;
        this.statusDAO = statusDAO;
    }

    public BigDecimal update(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer bookingId = booking.getId();
        if (bookingDAO.findOne(bookingId).isEmpty())
            throw new EntityNotFoundException("Booking not found");
        Booking daoBooking = bookingDAO.findOne(bookingId).get();

        if (daoBooking.getStatus().equals(Status.DONE))
            throw new IllegalArgumentException("Booking has already been completed");
        if (daoBooking.getStatus().equals(Status.CANCELLED))
            throw new IllegalArgumentException("Booking is cancelled");

        if (statusDAO.findIDByStatus(Status.DONE).isEmpty())
            throw new EntityNotFoundException("Status not found");
        Integer statusID = statusDAO.findIDByStatus(Status.DONE).get();

        if (bookingDAO.updateStatus(daoBooking.getId(), statusID, "PAGO").equals(Boolean.FALSE))
            throw new IllegalStateException("It was not possible to cancel the booking. Try again later.");

        Service service = daoBooking.getInfoService();
        return service.calculateComission();
    }
}
