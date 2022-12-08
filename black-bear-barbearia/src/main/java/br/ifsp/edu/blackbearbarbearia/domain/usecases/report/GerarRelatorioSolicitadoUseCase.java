package br.ifsp.edu.blackbearbarbearia.domain.usecases.report;

import br.ifsp.edu.blackbearbarbearia.domain.entities.booking.Booking;
import br.ifsp.edu.blackbearbarbearia.domain.entities.service.Service;
import br.ifsp.edu.blackbearbarbearia.domain.entities.user.User;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.booking.BookingDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.service.ServiceDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserDAO;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.user.UserInputRequestValidator;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.EntityNotFoundException;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Notification;
import br.ifsp.edu.blackbearbarbearia.domain.usecases.utils.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerarRelatorioSolicitadoUseCase {
    private final BookingDAO bookingDAO;

    public GerarRelatorioSolicitadoUseCase(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public List<Booking> findByPeriod(Date start, Date end) {
        if (bookingDAO.findAll().isEmpty())
            throw new EntityNotFoundException("No booking found.");

        if (start.after(Date.valueOf(LocalDate.now())) || end.after(Date.valueOf(LocalDate.now())) || start.after(end))
            throw new IllegalArgumentException("Period is invalid.");

        List<Booking> daoBookings = bookingDAO.findAll();
        var bookingStart = daoBookings.stream()
                .filter(booking -> booking.getNoFormattedDate().equals(start)).toList();

        List<Booking> bookings = new ArrayList<>(bookingStart);
        var bookingEnd = daoBookings.stream()
                .filter(booking -> booking.getNoFormattedDate().equals(end))
                .toList();
        bookings.addAll(bookingEnd);
        daoBookings = daoBookings.stream()
                .filter(booking -> booking.getNoFormattedDate().after(start))
                .filter(booking -> booking.getNoFormattedDate().before(end))
                .toList();
        bookings.addAll(daoBookings);

        return bookings;
    }
}
