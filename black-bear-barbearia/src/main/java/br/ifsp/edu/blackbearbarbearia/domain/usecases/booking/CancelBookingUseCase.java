package br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Status;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

public class CancelBookingUseCase {
    private final BookingDAO bookingDAO;
    private final StatusDAO statusDAO;
    public CancelBookingUseCase(BookingDAO bookingDAO, StatusDAO serviceDAO) {
        this.bookingDAO = bookingDAO;
        this.statusDAO = serviceDAO;
    }

    public Boolean update(Booking booking) {
        Validator<Booking> validator = new BookingInputRequestValidator();
        Notification notification = validator.validate(booking);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer bookingID = booking.getId();
        if (bookingDAO.findOne(bookingID).isEmpty())
            throw new EntityNotFoundException("Booking not found.");
        Booking daoBooking = bookingDAO.findOne(bookingID).get();

        if (daoBooking.getStatus().equals(Status.DONE))
            throw new IllegalArgumentException("Booking has already been completed");
        if (daoBooking.getStatus().equals(Status.CANCELLED))
            throw new IllegalArgumentException("Booking has already been canceled");

        if (statusDAO.findIDByStatus(Status.CANCELLED).isEmpty())
            throw new EntityNotFoundException("Status not found");
        Integer statusID = statusDAO.findIDByStatus(Status.CANCELLED).get();

        return bookingDAO.updateStatus(booking.getId(), statusID, "À PAGAR");
    }
}
